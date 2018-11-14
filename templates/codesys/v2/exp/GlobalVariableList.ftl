(* @NESTEDCOMMENTS := 'Yes' *)
(* @GLOBAL_VARIABLE_LIST := '${name}' *)
(* @PATH := '<#if path?has_content><#list path as dir>\/${dir}</#list></#if>' *)
(* @OBJECTFLAGS := '0, 8' *)
(* @SYMFILEFLAGS := '2048' *)
VAR_GLOBAL<#if constant!false> CONSTANT</#if>

<#if vars?has_content>
<#list vars as var>
    ${var.name}: ${var.type}<#if var.value??> := ${var.value}</#if>;
</#list>
</#if>

END_VAR

(* @OBJECT_END := '${name}' *)
(* @CONNECTIONS := ${name}
FILENAME : ''
FILETIME : 0
EXPORT : 0
NUMOFCONNECTIONS : 0
*)
