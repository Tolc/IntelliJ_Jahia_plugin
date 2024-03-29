# CND Language / [Jahia][1] Framework support for [IntelliJ IDEA][2]

[![release](https://img.shields.io/github/release/Tolc/IntelliJ_Jahia_Plugin.svg?style=flat-square)](https://github.com/Tolc/IntelliJ_Jahia_plugin/releases)

definitions.cnd files syntax highlighting, code completion, and other amazing stuff.

[![marketplace](marketplace.png)][3]

You can also rate it on the [JetBrains Plugins Repository][3].

## Requirements

Tested under IntelliJ IDEA from 14.1.4 to **2022.3.2**

IntelliJ plugins dependencies (optional): **JavaEE**, **JSP**, **Properties**. Requires Java **17** or higher (Java 8 before v2.2.0).

Jahia project versions: **6.6.x**, **7.x**, and **8.x**.



## Features


### CND Language

* Syntax highlighting
* Syntax checking and error highlighting
* Code completion
* Code formatting
* Find usages (ctrl-click/cmd-click) - for namespaces, nodetypes and nodetypes properties declarations (**not getting namespaces and nodetypes usages in .properties files for the moment though**)
* Go to declaration (ctrl-click/cmd-click) - for namespaces and nodetypes usages throughout the file
* Refactoring
    * Rename (shift+f6) - for namespaces, nodetypes and nodetypes properties declarations (**not renaming namespaces and nodetypes usages in .properties files for the moment though**)
* File icon ![CND file icon](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/cnd.png)
* Line markers for namespaces ![namespace](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/namespace.png) and nodetypes ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeType.png) / mixins ![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixin.png)
* Code folding - for namespaces and nodetypes
* Brace matching - closing brackets and parenthesis are automatically inserted where authorized by the syntax
* Commenter (ctrl+/) - to comment lines of code



### Jahia Framework
[![Jahia](resources/jahia/jahia.png)][1]

#### Compatible with Jahia **6.6.x**, **7.x**, and **8.x**.

* CND files features:
    * Helpers/Quickfixes (alt+enter on nodetype name)
        * Create nodetype and nodetype properties translations - only appears if no translation is found
        * Create new view - opens a popup that lets you choose the new view parameters, creates view and cache properties files, and also creates the folders if they don't already exist. **If the view is a JSP, the new view also contains code to access all the node properties and sub nodes.**
    * Nodetype folders icon in Project View (![view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeTypeFolder.png) or ![hidden view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixinFolder.png) if mixin)
    * View files grouping (creates a virtual folder ![view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/viewBig.png) - or ![hidden view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/viewBigHidden.png) if hidden view - in the Project View)
    * Completion and other features on Jahia nodetypes (embedded Jahia base and main modules .cnd files) - a local library called 'jahia-plugin-base-cnd-files' is automatically added to the module dependencies if the module contains at least one cnd file (in case you have deleted this dependency, no panic, it comes back after re-opening the project)

* Other files/languages features:
    * JSP
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
        * `<template:module/>` line marker, 'view' attribute completion and reference ![template module](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/templateModule.png) (both are affected by 'templateType' attribute if provided)
        * `<template:include/>` line marker, 'view' attribute completion and reference ![template include](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/templateInclude.png) (both are affected by 'templateType' attribute if provided)
        * Properties and property type completion in `${currentNode.properties.propertyName}`, `${currentNode.properties['propertyName']}` and `<jcr:nodeProperty/>`
        * `<template:addResources/>` line marker, completion and reference
    * Java
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
    * XML
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
    * Properties (resource bundles)
        * Translations keys syntax highlighting & line markers for namespaces ![namespace](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/namespace.png), nodetypes ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/mixin.png) and properties ![property](src/fr/tolc/jahia/intellij/plugin/cnd/icons/img/property.png)
        * Translations keys nodetypes and properties completion
        * Go to namespace, nodetype or property declaration from translations keys (ctrl-click/cmd-click)
        * Error message if adding translations for a choicelist on a non-choicelist property
* IntelliJ
    * Jahia Tool Window



## Roadmap (?)

##### This is just the beginning.

* Improved cache .properties support:
    * Resource bundles .properties extension, to be able to find namespaces and nodetypes usages in it
    * View cache .properties custom language
* Plugin settings
* Make helpers/quickfixes accessible through Actions in menus
* Groovy support

#### Don't hesitate to request features / suggest enhancements by opening an Issue.

--

##### This plugin is neither official nor endorsed by [Jahia][1] in any way. I am just a guy who loves this CMS and wanted to make working with it easier for everyone (or at least everyone using IntelliJ, duh).

[1]: https://www.jahia.com/
[2]: https://www.jetbrains.com/idea/
[3]: https://plugins.jetbrains.com/plugin/9221-cnd-language--jahia-framework/