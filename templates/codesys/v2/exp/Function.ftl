
(* @NESTEDCOMMENTS := 'No' *)
(* @PATH := '<#if pou.path?has_content><#list pou.path as dir>\/${dir}</#list></#if>' *)
(* @OBJECTFLAGS := '0, 8' *)
(* @SYMFILEFLAGS := '2048' *)
FUNCTION ${pou.name} : ${interface.returnType}
VAR_INPUT
<#if interface.inputVars?has_content>
<#list interface.inputVars as inputVar>
    ${inputVar.name}: ${inputVar.type};
</#list>
</#if>
END_VAR
<#if interface.inOutVars?has_content>
VAR_IN_OUT
<#list interface.inOutVars as inOutVar>
    ${inOutVar.name}: ${inOutVar.type};
</#list>
END_VAR
</#if>
VAR
<#if interface.vars?has_content>
<#list interface.vars as var>
    ${var.name}: ${var.type};
</#list>
</#if>
END_VAR
(* @END_DECLARATION := '0' *)
<#if body.instructions?has_content>
<#list body.instructions as instruction>
${instruction}
</#list>
</#if>
END_FUNCTION
