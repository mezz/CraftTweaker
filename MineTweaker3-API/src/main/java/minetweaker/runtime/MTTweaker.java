/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.runtime;

import minetweaker.*;
import minetweaker.api.item.IIngredient;
import minetweaker.api.loadstages.EnumLoadingStage;
import minetweaker.runtime.providers.ScriptProviderMemory;
import stanhebben.zenscript.ZenModule;
import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.parser.ParseException;

import java.io.*;
import java.util.*;

import static stanhebben.zenscript.ZenModule.*;

/**
 * @author Stan Hebben
 */
public class MTTweaker implements ITweaker {
	
	private static boolean DEBUG = false;
	private final LinkedHashMap<EnumLoadingStage, LinkedList<IAction>> actions = new LinkedHashMap<>();
	
	//    private final List<IUndoableAction> actions = new ArrayList<>();
	private final Set<IAction> wereStuck = new LinkedHashSet<>();
	private final Map<Object, IAction> stuckOverridable = new HashMap<>();
	
	private IScriptProvider scriptProvider;
	private byte[] scriptData;
	
	@Override
	public byte[] getStagedScriptData() {
		return ScriptProviderMemory.collect(scriptProvider);
	}
	
	@Override
	public void apply(IAction action) {
		Object overrideKey = action.getOverrideKey();
		if(wereStuck.contains(action)) {
			wereStuck.remove(action);
			MineTweakerAPI.logInfo(action.describe());
			if(overrideKey != null) {
				stuckOverridable.remove(overrideKey);
			}
		} else {
			if(overrideKey != null && stuckOverridable.containsKey(overrideKey)) {
				wereStuck.remove(stuckOverridable.get(overrideKey));
				stuckOverridable.remove(overrideKey);
			}
			if(!actions.getOrDefault(action.getLoadingStage(), new LinkedList<>()).contains(action))
				if(MineTweakerAPI.currentStage == action.getLoadingStage() || MineTweakerImplementationAPI.reloading || MineTweakerAPI.currentStage.getPriority() > action.getLoadingStage().getPriority()) {
					MineTweakerAPI.logInfo(action.describe());
					action.apply();
				}
		}
		LinkedList<IAction> actionList = actions.getOrDefault(action.getLoadingStage(), new LinkedList<>());
		actionList.add(action);
		actions.put(action.getLoadingStage(), actionList);
		System.out.println(MineTweakerAPI.currentStage + ":" + actions.keySet());
		System.out.println(actions.values());
		System.out.println(actions.size());
	}
	
	@Override
	public void remove(IIngredient items) {
		GlobalRegistry.remove(items);
	}
	
	@Override
	public List<IAction> rollback() {
		List<IAction> stuck = new ArrayList<>();
		LinkedList<IUndoableAction> actionList = new LinkedList<>();
		System.out.println(actions.size());
		actions.entrySet().stream().forEachOrdered(action -> {
			System.out.println(action);
			action.getValue().stream().forEach(act -> {
				if(act instanceof IUndoableAction)
					actionList.add((IUndoableAction) act);
			});
			//			if(action.getValue() instanceof IUndoableAction)
			//			actionList.add((IUndoableAction) action.getValue());
		});
		System.out.println("ACtion list Size" + actionList.size());
		List<IUndoableAction> removedActions = new LinkedList<>();
		for(int i = actionList.size() - 1; i >= 0; i--) {
			IUndoableAction action = actionList.get(i);
			removedActions.add(action);
			if(action.canUndo()) {
				MineTweakerAPI.logInfo(action.describeUndo());
				action.undo();
			} else {
				MineTweakerAPI.logInfo("[Stuck] 1 " + action.describe());
				MineTweakerAPI.logInfo("[Stuck] 2 " + action.describeUndo());
				MineTweakerAPI.logInfo("[Stuck] 3 " + action.toString());
				stuck.add(0, action);
				wereStuck.add(action);
				
				Object overrideKey = action.getOverrideKey();
				if(overrideKey != null) {
					stuckOverridable.put(overrideKey, action);
				}
			}
		}
		actionList.clear();
//		actions.clear();
		removedActions.forEach(action -> {
			LinkedList<IAction> removed = actions.getOrDefault(action.getLoadingStage(), new LinkedList<>());
			removed.remove(action);
		});
		return stuck;
	}
	
	@Override
	public void setScriptProvider(IScriptProvider provider) {
		scriptProvider = provider;
	}
	
	@Override
	public void load() {
		System.out.println("Loading scripts");
		
		scriptData = ScriptProviderMemory.collect(scriptProvider);
		Set<String> executed = new HashSet<>();
		
		Iterator<IScriptIterator> scripts = scriptProvider.getScripts();
		while(scripts.hasNext()) {
			IScriptIterator script = scripts.next();
			
			if(!executed.contains(script.getGroupName())) {
				executed.add(script.getGroupName());
				
				Map<String, byte[]> classes = new HashMap<>();
				IEnvironmentGlobal environmentGlobal = GlobalRegistry.makeGlobalEnvironment(classes);
				
				List<ZenParsedFile> files = new ArrayList<>();
				
				while(script.next()) {
					Reader reader = null;
					try {
						reader = new InputStreamReader(new BufferedInputStream(script.open()), "UTF-8");
						
						String filename = script.getName();
						String className = extractClassName(filename);
						
						ZenTokener parser = new ZenTokener(reader, environmentGlobal.getEnvironment());
						ZenParsedFile pfile = new ZenParsedFile(filename, className, parser, environmentGlobal);
						files.add(pfile);
					} catch(IOException ex) {
						MineTweakerAPI.logError("Could not load script " + script.getName() + ": " + ex.getMessage());
					} catch(ParseException ex) {
						// ex.printStackTrace();
						MineTweakerAPI.logError("Error parsing " + ex.getFile().getFileName() + ":" + ex.getLine() + " -- " + ex.getExplanation());
					} catch(Exception ex) {
						MineTweakerAPI.logError("Error loading " + script.getName() + ": " + ex.toString(), ex);
					}
					
					if(reader != null) {
						try {
							reader.close();
						} catch(IOException ignored) {
						}
					}
				}
				
				try {
					String filename = script.getGroupName();
					System.out.println("MineTweaker: Loading " + filename);
					compileScripts(filename, files, environmentGlobal, DEBUG);
					
					// execute scripts
					ZenModule module = new ZenModule(classes, MineTweakerAPI.class.getClassLoader());
					module.getMain().run();
				} catch(Throwable ex) {
					MineTweakerAPI.logError("Error executing " + script.getGroupName() + ": " + ex.getMessage(), ex);
				}
			}
		}
		
		if(wereStuck.size() > 0) {
			MineTweakerAPI.logWarning(Integer.toString(wereStuck.size()) + " modifications were stuck");
			for(IAction action : wereStuck) {
				MineTweakerAPI.logInfo("Stuck: " + action.describe());
			}
		}
	}
	
	@Override
	public byte[] getScriptData() {
		return scriptData;
	}
	
	
	@Override
	public LinkedHashMap<EnumLoadingStage, LinkedList<IAction>> getActions() {
		return actions;
	}
	
	@Override
	public void enableDebug() {
		DEBUG = true;
	}
}
