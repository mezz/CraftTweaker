/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.formatting;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.formatting.IFormattedText")
public interface IFormattedText {
    @ZenOperator(OperatorType.ADD)
    IFormattedText add(IFormattedText other);

    @ZenOperator(OperatorType.CAT)
    IFormattedText cat(IFormattedText other);
    
    @ZenGetter("string")
    String asString();
    
}
