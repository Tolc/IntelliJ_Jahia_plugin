# CND Language / [Jahia][1] Framework support for [IntelliJ IDEA][3]

[![release](https://img.shields.io/github/release/Tolc/IntelliJ_Jahia_Plugin.svg?style=flat-square)](https://github.com/Tolc/IntelliJ_Jahia_plugin/releases)

definitions.cnd files syntax highlighting, code completion, and other amazing stuff.


##Requirements

Tested under IntelliJ IDEA from 14.1.4 to 2016.3.3 (can't see why it wouldn't work with older versions though).

IntelliJ plugins dependencies: **jsp**, **properties**. Java **8** needed.
 
Jahia project versions: **6.6.x** or **7.x**



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
 * File icon ![CND file icon](src/fr/tolc/jahia/intellij/plugin/cnd/icons/cnd.png) 
 * Line markers for namespaces ![namespace](src/fr/tolc/jahia/intellij/plugin/cnd/icons/namespace.png) and nodetypes ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeType.png) / mixins ![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixin.png)
 * Code folding - for namespaces and nodetypes
 * Brace matching - closing brackets and parenthesis are automatically inserted where authorized by the syntax
 * Commenter (ctrl+/) - to comment lines of code



### Jahia Framework
![Jahia][2]

#### Compatible with Jahia versions **6.6.x** & **7.x**

 * CND files features:
     * Helpers/Quickfixes (alt+enter on nodetype name)
        * Create nodetype and nodetype properties translations - only appears if no translation is found
        * Create new view - opens a popup that lets you choose the new view parameters, creates view and cache properties files, and also creates the folders if they don't already exist. **If the view is a JSP, the new view also contains code to access all the node properties and sub nodes.**
     * Nodetype folders icon in Project View (![view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeTypeFolder.png) or ![hidden view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixinFolder.png) if mixin)
     * View files grouping (creates a virtual folder ![view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/viewBig.png) - or ![hidden view folder](src/fr/tolc/jahia/intellij/plugin/cnd/icons/viewBigHidden.png) if hidden view - in the Project View)
     * Completion and other features on Jahia nodetypes (embedded Jahia base and main modules .cnd files) - a local library called 'jahia-plugin-base-cnd-files' is automatically added to the module dependencies if the module contains at least one cnd file (in case you have deleted this dependency, no panic, it comes back after re-opening the project)

 * Other files/languages features:
     * JSP
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
     * Java
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
     * XML
        * Nodetypes usages highlighting & line markers ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixin.png)
        * Nodetypes completion
        * Go to nodetype declaration (ctrl-click/cmd-click)
        * Unknown nodetype error highlighting
        * Create nodetype quickfix (alt+enter on nodetype name) - if known namespace but unknown nodetype
     * Properties (resource bundles)
        * Translations keys syntax highlighting & line markers for namespaces ![namespace](src/fr/tolc/jahia/intellij/plugin/cnd/icons/namespace.png), nodetypes ![nodetype](src/fr/tolc/jahia/intellij/plugin/cnd/icons/nodeType.png)/![mixin](src/fr/tolc/jahia/intellij/plugin/cnd/icons/mixin.png) and properties ![property](src/fr/tolc/jahia/intellij/plugin/cnd/icons/property.png)
        * Translations keys nodetypes and properties completion
        * Go to namespace, nodetype or property declaration from translations keys (ctrl-click/cmd-click)
        * Error message if adding translations for a choicelist on a non-choicelist property



## Roadmap

##### This is just the beginning.

 * Improved JSP support:
    * Properties and property type completion in `${currentNode.properties.propertyName}`, `${currentNode.properties['propertyName']}` and `jcr:property`
    * Views completion in `template:module`
 * Improved Properties support:
    * Resource bundles .properties extension, to be able to find namespaces and nodetypes usages in it
    * View cache .properties custom language     
 * Plugin settings
 * Make helpers/quickfixes accessible through Actions in menus
 * Groovy support

#### Don't hesitate to request features / suggest enhancements by opening an Issue.

--

##### This plugin is neither official nor endorsed by [Jahia][1] in any way. I am just a guy who loves this CMS and wanted to make working with it easier for everyone (or at least everyone using IntelliJ, duh).

 [1]: https://www.jahia.com/
 [2]: https://www.jahia.com/files/live/sites/jahiacom/files/logo-jahia-2016.png
 [3]: https://www.jetbrains.com/idea/