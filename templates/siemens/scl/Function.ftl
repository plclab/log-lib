FUNCTION "${block.name}" : ${interface.returnType}
{ S7_Optimized_Access := 'FALSE' }
VERSION : 0.1
<#if interface.inputVars?has_content>
   VAR_INPUT 
   <#list interface.inputVars as inputVar>
      ${inputVar.name} : ${inputVar.type};
   </#list>
   END_VAR

</#if>
<#if interface.inOutVars?has_content>
   VAR_IN_OUT
   <#list interface.inOutVars as inOutVar>
      ${inOutVar.name} : ${inOutVar.type};
   </#list>
   END_VAR
   
</#if>
<#if interface.tempVars?has_content>
   VAR_TEMP 
   <#list interface.tempVars as tempVar>
      ${tempVar.name} : ${tempVar.type};
   </#list>
   END_VAR

</#if>

BEGIN
<#if body.instructions?has_content>
<#list body.instructions as instruction>
	${instruction}
</#list>
</#if>
END_FUNCTION

