(function(e){function t(t){for(var n,i,r=t[0],l=t[1],c=t[2],u=0,h=[];u<r.length;u++)i=r[u],Object.prototype.hasOwnProperty.call(s,i)&&s[i]&&h.push(s[i][0]),s[i]=0;for(n in l)Object.prototype.hasOwnProperty.call(l,n)&&(e[n]=l[n]);d&&d(t);while(h.length)h.shift()();return a.push.apply(a,c||[]),o()}function o(){for(var e,t=0;t<a.length;t++){for(var o=a[t],n=!0,i=1;i<o.length;i++){var r=o[i];0!==s[r]&&(n=!1)}n&&(a.splice(t--,1),e=l(l.s=o[0]))}return e}var n={},i={app:0},s={app:0},a=[];function r(e){return l.p+"js/"+({}[e]||e)+"."+{"chunk-0d9ba75c":"7606b23a","chunk-2d0b9d35":"a3975176","chunk-71367435":"a2fe4c18","chunk-76d4c4d9":"242c7df5","chunk-7b8aceec":"377e4ea2"}[e]+".js"}function l(t){if(n[t])return n[t].exports;var o=n[t]={i:t,l:!1,exports:{}};return e[t].call(o.exports,o,o.exports,l),o.l=!0,o.exports}l.e=function(e){var t=[],o={"chunk-0d9ba75c":1,"chunk-71367435":1,"chunk-76d4c4d9":1,"chunk-7b8aceec":1};i[e]?t.push(i[e]):0!==i[e]&&o[e]&&t.push(i[e]=new Promise((function(t,o){for(var n="css/"+({}[e]||e)+"."+{"chunk-0d9ba75c":"860aa8f7","chunk-2d0b9d35":"31d6cfe0","chunk-71367435":"baa9b8d1","chunk-76d4c4d9":"7de150c7","chunk-7b8aceec":"7397764e"}[e]+".css",s=l.p+n,a=document.getElementsByTagName("link"),r=0;r<a.length;r++){var c=a[r],u=c.getAttribute("data-href")||c.getAttribute("href");if("stylesheet"===c.rel&&(u===n||u===s))return t()}var h=document.getElementsByTagName("style");for(r=0;r<h.length;r++){c=h[r],u=c.getAttribute("data-href");if(u===n||u===s)return t()}var d=document.createElement("link");d.rel="stylesheet",d.type="text/css",d.onload=t,d.onerror=function(t){var n=t&&t.target&&t.target.src||s,a=new Error("Loading CSS chunk "+e+" failed.\n("+n+")");a.code="CSS_CHUNK_LOAD_FAILED",a.request=n,delete i[e],d.parentNode.removeChild(d),o(a)},d.href=s;var f=document.getElementsByTagName("head")[0];f.appendChild(d)})).then((function(){i[e]=0})));var n=s[e];if(0!==n)if(n)t.push(n[2]);else{var a=new Promise((function(t,o){n=s[e]=[t,o]}));t.push(n[2]=a);var c,u=document.createElement("script");u.charset="utf-8",u.timeout=120,l.nc&&u.setAttribute("nonce",l.nc),u.src=r(e);var h=new Error;c=function(t){u.onerror=u.onload=null,clearTimeout(d);var o=s[e];if(0!==o){if(o){var n=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;h.message="Loading chunk "+e+" failed.\n("+n+": "+i+")",h.name="ChunkLoadError",h.type=n,h.request=i,o[1](h)}s[e]=void 0}};var d=setTimeout((function(){c({type:"timeout",target:u})}),12e4);u.onerror=u.onload=c,document.head.appendChild(u)}return Promise.all(t)},l.m=e,l.c=n,l.d=function(e,t,o){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:o})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var o=Object.create(null);if(l.r(o),Object.defineProperty(o,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var n in e)l.d(o,n,function(t){return e[t]}.bind(null,n));return o},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="/",l.oe=function(e){throw console.error(e),e};var c=window["webpackJsonp"]=window["webpackJsonp"]||[],u=c.push.bind(c);c.push=t,c=c.slice();for(var h=0;h<c.length;h++)t(c[h]);var d=u;a.push([0,"chunk-vendors"]),o()})({0:function(e,t,o){e.exports=o("56d7")},"25f7":function(e,t,o){},2919:function(e,t,o){"use strict";var n="/api";t["a"]={HOST_URL:n}},"559a":function(e,t,o){"use strict";var n=o("a9de"),i=o.n(n);i.a},"56d7":function(e,t,o){"use strict";o.r(t);o("e260"),o("e6cf"),o("cca6"),o("a79d");var n=o("2b0e"),i=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{attrs:{id:"app"}},[o("router-view")],1)},s=[],a={created:function(){this.$store.commit("setWebsiteTitle",document.title)}},r=a,l=o("2877"),c=Object(l["a"])(r,i,s,!1,null,null,null),u=c.exports,h=(o("d3b7"),o("8c4f")),d=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("el-container",[o("el-header",{staticClass:"header noselect",staticStyle:{"background-color":"rgb(248, 248, 248)"},nativeOn:{contextmenu:function(e){e.preventDefault()}}},[o("div",[o("span",{on:{click:function(t){e.showAside=!e.showAside}}},[o("span",[e._v("📕")]),o("strong",[e._v(" "+e._s(e.pageTitle))])]),o("span",{staticClass:"fa fa-bars",staticStyle:{color:"rgb(248, 248, 248)","font-size":"25px","margin-top":"20px"}}),o("el-dropdown",{staticClass:"pull-right",attrs:{placement:"top-start"}},[o("span",{staticClass:"el-dropdown-link"},[o("i",{staticClass:"fa fa-bars noselect",staticStyle:{color:"black","font-size":"25px","margin-top":"20px"}})]),o("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[o("router-link",{staticStyle:{color:"black","text-decoration":"none"},attrs:{to:"/setting"}},[o("el-dropdown-item",{staticClass:"markidea-dropdown-item"},[e._v(" 设置 ")])],1),o("router-link",{staticStyle:{color:"black","text-decoration":"none"},attrs:{to:"/admin"}},[e.isAdminUser?o("el-dropdown-item",{staticClass:"markidea-dropdown-item"},[e._v(" 网站管理 ")]):e._e()],1),o("el-dropdown-item",{staticClass:"markidea-dropdown-item",attrs:{divided:""},nativeOn:{click:function(t){e.showAboutPage=!e.showAboutPage}}},[e._v("关于")]),o("el-dropdown-item",{staticClass:"markidea-dropdown-item",attrs:{divided:""},nativeOn:{click:function(t){return e.handleLogout()}}},[e._v("注销")])],1)],1)],1)]),o("el-container",[o("el-aside",{directives:[{name:"show",rawName:"v-show",value:e.showAside,expression:"showAside "}],staticClass:"notebooklist noselect",attrs:{width:"200px"}},[o("div",{staticStyle:{margin:"5px"}},[o("el-input",{attrs:{clearable:"",placeholder:"搜索笔记"},on:{blur:function(t){e.keyWord=null}},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.searchNotes(t)}},model:{value:e.keyWord,callback:function(t){e.keyWord=t},expression:"keyWord"}})],1),o("div",{staticClass:"notebook",staticStyle:{"padding-bottom":"10px",color:"grey"},on:{contextmenu:function(e){e.preventDefault()}}},[o("span",{staticStyle:{"font-size":"15px"}},[o("strong",[e._v("笔记本")])]),o("el-popover",{attrs:{placement:"bottom"},model:{value:e.newNoteBookVisible,callback:function(t){e.newNoteBookVisible=t},expression:"newNoteBookVisible"}},[o("div",[o("el-input",{attrs:{placeholder:"新笔记本名"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleCreateNotebook(t)}},model:{value:e.newNotebookName,callback:function(t){e.newNotebookName=t},expression:"newNotebookName"}})],1),o("i",{staticClass:"fa fa-plus-square-o pull-right ",staticStyle:{color:"grey","margin-top":"3px","font-size":"15px"},attrs:{slot:"reference",title:"新建笔记本"},slot:"reference"})])],1),e._l(e.notebookList,(function(t){return o("div",{directives:[{name:"contextmenu",rawName:"v-contextmenu:notebookRightMenu",arg:"notebookRightMenu"}],key:t.notebookName,class:{chosenNotebook:e.curNotebook.notebookName===t.notebookName}},[e.toRenameNotebookName&&e.toRenameNotebookName.length>0&&e.toRenameNotebookName===t.notebookName?o("div",{staticClass:"notebook"},[o("el-input",{attrs:{placeholder:"新笔记本名"},on:{blur:function(t){e.toRenameNotebookName=null}},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleRenameNotebook(t)}},model:{value:e.destNotebookName,callback:function(t){e.destNotebookName=t},expression:"destNotebookName"}})],1):o("div",{staticClass:"notebook",on:{click:function(o){return e.selectNoteList(t.notebookName)}}},[e._v("📙 "+e._s(t.notebookName))])])})),o("el-collapse",{staticStyle:{"background-color":"rgb(248, 248, 248)"},attrs:{accordion:""},nativeOn:{contextmenu:function(e){e.preventDefault()}}},[o("el-collapse-item",{staticStyle:{"background-color":"rgb(248, 248, 248)"}},[o("template",{slot:"title"},[o("div",{directives:[{name:"contextmenu",rawName:"v-contextmenu:delNotesRightMenu",arg:"delNotesRightMenu"}],staticClass:"notebook",staticStyle:{"font-size":"15px","padding-bottom":"10px",color:"grey","border-bottom":"0px","background-color":"rgb(248, 248, 248)"}},[e._v("垃圾桶")])]),e._l(e.delNoteList,(function(t){return o("div",{directives:[{name:"contextmenu",rawName:"v-contextmenu:delNoteRightMenu",arg:"delNoteRightMenu"}],key:t.id,staticClass:"delnote"},[e._v(" "+e._s(t.notebook)+"/"+e._s(t.title)+" ")])}))],2)],1)],2),o("el-aside",{directives:[{name:"show",rawName:"v-show",value:e.showAside&&e.showNotes,expression:"showAside && showNotes"}],staticClass:"noselect noteList",attrs:{width:"300px"},nativeOn:{contextmenu:function(e){e.preventDefault()}}},[o("div",{staticClass:"notebookInfo"},[o("div",[o("span",{staticClass:"noselect"},[e._v(" 📙 ")]),e._v(e._s(e.curNotebook.notebookName)+" "),null!==e.curNotebook.notebookName?o("el-popover",{attrs:{placement:"bottom",trigger:"click"},model:{value:e.newNoteVisible,callback:function(t){e.newNoteVisible=t},expression:"newNoteVisible"}},[o("div",[o("el-input",{attrs:{placeholder:"新笔记名"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleCreateNote(t)}},model:{value:e.newNoteTitle,callback:function(t){e.newNoteTitle=t},expression:"newNoteTitle"}})],1),o("i",{staticClass:"fa fa-plus-square-o pull-right",staticStyle:{color:"grey","margin-top":"8px","font-size":"15px"},attrs:{slot:"reference",title:"新建笔记"},slot:"reference"})]):e._e()],1)]),e._l(e.curNotebook.noteList,(function(t){return o("div",{directives:[{name:"contextmenu",rawName:"v-contextmenu:noteRightMenu",arg:"noteRightMenu"}],key:t.title,staticClass:"note",class:{chosenNote:e.curNote.noteTitle===t.title},on:{click:function(o){return e.selectNote(t.title,e.curNotebook.notebookName)}}},[o("div",{staticClass:"notetitle"},[o("span",{staticClass:"noselect"},[e._v("📝 ")]),e._v(e._s(t.title)+" "),o("span",{staticClass:"pull-right"},[o("span",{staticClass:"vditor-tooltipped vditor-tooltipped__nw ",attrs:{"aria-label":"修改尚未保存"}},[2===t.status?o("i",{staticClass:"fa fa-warning  ",staticStyle:{color:"lightgrey"},attrs:{"aria-label":"修改尚未保存"}}):e._e()]),o("span",{staticClass:"vditor-tooltipped vditor-tooltipped__nw",attrs:{"aria-label":"公开笔记"}},[t.articleId?o("i",{staticClass:" fa fa-eye ",staticStyle:{color:"lightgrey","padding-left":"5px"}}):e._e()])])]),o("div",{staticClass:"notePreview"},[e._v(e._s(t.previewContent))])])}))],2),e.showAside&&e.showSearch?o("el-aside",{staticClass:"noselect noteList",attrs:{width:"300px"}},[o("div",{staticClass:"notebookInfo"},[o("div",[o("span",{staticClass:"noselect"},[e._v(" 🔎 ")]),e._v(e._s(e.searchNotesName)+" ")])]),e._l(e.searchResult,(function(t){return o("div",{key:t.title+"search",staticClass:"note noselect",class:{chosenNote:e.curNote.noteTitle===t.title&&e.curNote.notebookName===t.notebookName},on:{click:function(o){return e.selectNote(t.title,t.notebookName)}}},[o("div",{staticClass:"notetitle"},[o("span",{staticClass:"noselect"},[e._v("📝 ")]),e._v(e._s(t.notebookName)+"/"+e._s(t.title))]),o("div",[e._v(e._s(t.previewContent))])])}))],2):e._e(),o("el-main",{directives:[{name:"show",rawName:"v-show",value:!e.isMobile||!e.showAside,expression:"!isMobile || !showAside"}],staticClass:"editor",staticStyle:{overflow:"unset"}},[o("Editor",{ref:"editor",on:{showHistory:e.handleShowHistory,saveContent:e.handleSaveContent,renameTitle:e.doHandleRenameTitle}})],1),e.showHistory?o("el-aside",{attrs:{width:"220px"}},[o("el-dialog",{attrs:{visible:e.showHistoryPreview,width:"60%"},on:{"update:visible":function(t){e.showHistoryPreview=t},opened:e.handleOpenHistoryPrev}},[o("history-preview",{ref:"historyPreview"})],1),o("div",{staticStyle:{"padding-left":"10px","padding-top":"15px"}},[o("div",{staticStyle:{"font-size":"18px","padding-bottom":"5px"}},[o("span",{staticStyle:{"font-size":"20px"}},[e._v("🎛️ ")]),o("strong",[e._v("历史版本")])]),e._l(e.curNoteVersion,(function(t){return o("div",{key:t.ref},[o("span",{staticStyle:{"font-size":"13px","margin-right":"5px"}},[e._v(e._s(t.date))]),o("el-button",{directives:[{name:"show",rawName:"v-show",value:t.ref!==e.curRef,expression:"version.ref !== curRef"}],staticStyle:{"font-size":"13px"},attrs:{size:"mini",type:"text"},on:{click:function(o){return e.handleRecover(t.ref)}}},[e._v("恢复")]),o("el-button",{directives:[{name:"show",rawName:"v-show",value:t.ref!==e.curRef,expression:"version.ref !== curRef"}],staticStyle:{"font-size":"13px"},attrs:{size:"mini",type:"text"},on:{click:function(o){return e.handlePreviewHistory(t.ref)}}},[e._v("预览")])],1)}))],2)],1):e._e()],1)],1),o("v-contextmenu",{ref:"noteRightMenu",staticClass:"rightMenu",attrs:{theme:"dark"},on:{contextmenu:e.handleNoteRightMenu}},[e.showRenameOption?o("v-contextmenu-item",{on:{click:e.handleRenameNote}},[e._v("重命名")]):e._e(),o("v-contextmenu-item",{on:{click:e.handleDelNote}},[e._v("删除")]),o("v-contextmenu-submenu",{attrs:{title:"移动至"}},e._l(e.notebookList,(function(t){return o("div",{key:t.notebookName},[e.curNotebook.notebookName!==t.notebookName?o("v-contextmenu-item",{on:{click:function(o){return e.handleMoveNote(t.notebookName)}}},[e._v(" 📙 "+e._s(t.notebookName)+" ")]):e._e()],1)})),0),o("v-contextmenu-submenu",{attrs:{title:"复制到"}},e._l(e.notebookList,(function(t){return o("div",{key:t.notebookName},[e.curNotebook.notebookName!==t.notebookName?o("v-contextmenu-item",{on:{click:function(o){return e.handleCopyNote(t.notebookName)}}},[e._v(" 📙 "+e._s(t.notebookName)+" ")]):e._e()],1)})),0)],1),o("v-contextmenu",{ref:"notebookRightMenu",staticClass:"rightMenu",attrs:{theme:"dark"},on:{contextmenu:e.handleNotebookRightMenu}},[o("v-contextmenu-item",{on:{click:e.handleDelNotebook}},[e._v("删除")]),o("v-contextmenu-item",{on:{click:e.allowRenameNotebook}},[e._v("重命名")])],1),o("v-contextmenu",{ref:"delNotesRightMenu",staticClass:"rightMenu",attrs:{theme:"dark"}},[o("v-contextmenu-item",{on:{click:e.clearAllDelNotes}},[e._v("清空")])],1),o("v-contextmenu",{ref:"delNoteRightMenu",staticClass:"rightMenu",attrs:{theme:"dark"},on:{contextmenu:e.handleDelNoteRightMenu}},[o("v-contextmenu-item",{on:{click:e.clearDelNote}},[e._v("删除")]),o("v-contextmenu-item",{on:{click:e.recoverDelNote}},[e._v("恢复")])],1),o("el-dialog",{attrs:{modal:!1,"show-close":!1,center:!0,visible:e.showAboutPage,width:"300px"},on:{"update:visible":function(t){e.showAboutPage=t}}},[o("strong",{staticStyle:{"font-size":"30px"},attrs:{slot:"title"},slot:"title"},[e._v("📕 MarkIdea")]),o("about")],1)],1)},f=[],m=(o("4160"),o("159b"),o("b85c")),b=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("div",{directives:[{name:"show",rawName:"v-show",value:e.notebookName&&e.title&&e.title.length>0,expression:"notebookName && title && title.length > 0"}],staticClass:"title"},[e.showEditTitle?e._e():o("span",{staticClass:"noselect"},[e._v(" ✏️ ")]),e.showEditTitle?e._e():o("span",[e._v(e._s(e.title))]),e.contentModfied?o("span",{staticStyle:{color:"lightgrey","font-size":"14px","padding-left":"5px","font-weight":"lighter"}},[e._v("- 已编辑")]):e._e(),e.showEditTitle?o("el-input",{on:{blur:function(t){return e.setTitleEditable(!1)}},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.renameTitle(t)}},model:{value:e.newTitle,callback:function(t){e.newTitle=t},expression:"newTitle"}}):e._e()],1),o("div",{directives:[{name:"show",rawName:"v-show",value:e.notebookName&&e.title&&e.title.length>0,expression:"notebookName && title && title.length > 0"}],staticClass:"vditor",attrs:{id:"vditor"}})])},v=[],N=o("ff41"),k=o.n(N),p=o("2919"),g=o("bc3a"),w=o.n(g),y={name:"Editor",data:function(){var e=this;return{showEditTitle:!1,config:{headers:{token:this.$store.getters.getToken,username:this.$store.getters.getUsername}},editorConfig:this.$store.getters.getEditorConfig,contentModfied:!1,notebookName:null,originContent:"",title:"",newTitle:null,vditor:null,toolbar:[{hotkey:"⌘H",icon:"<i  class='fa fa-header fa-lg'></i>",name:"headings",tipPosition:"ne"},{hotkey:"⌘B",name:"bold",prefix:"**",suffix:"**",tipPosition:"ne",icon:"<i class='fa fa-bold fa-lg'></i>"},{hotkey:"⌘I",icon:"<i class='fa fa-italic fa-lg'></i>",name:"italic",prefix:"*",suffix:"*",tipPosition:"ne"},{hotkey:"⌘L",icon:"<i class='fa fa-strikethrough fa-lg'></i>",name:"strike",prefix:"~~",suffix:"~~",tipPosition:"ne"},{hotkey:"⌘K",icon:"<i class='fa fa-link fa-lg'></i>",name:"link",prefix:"[",suffix:"](https://)",tipPosition:"n"},"table","|",{icon:"<i class='fa fa-list-ul fa-lg'></i>",name:"list",prefix:"* ",tipPosition:"n"},{hotkey:"⌘O",icon:"<i class='fa fa-list-ol fa-lg'></i>",name:"ordered-list",prefix:"1. ",tipPosition:"n"},{hotkey:"⌘J",icon:"<i class='fa fa-check-square-o fa-lg'></i>",name:"check",prefix:"* [ ] ",tipPosition:"n"},{hotkey:"⌘⇧I",icon:"<i class='fa fa-outdent fa-lg'></i>",name:"outdent",tipPosition:"n"},{hotkey:"⌘⇧O",icon:"<i class='fa fa-indent fa-lg'></i>",name:"indent",tipPosition:"n"},"|",{hotkey:"⌘;",icon:"<i class='fa fa-quote-left fa-lg'></i>",name:"quote",prefix:"> ",tipPosition:"n"},"line","code","inline-code","|","upload","undo","redo","|","edit-mode",{name:"history",tip:"历史版本",icon:"<i class='fa fa-history fa-lg'></i>",click:function(){e.showHistory()}},"outline",{hotkey:"⌘s",name:"save",tip:"保存",icon:'<i class="fa fa-save fa-lg"/>',click:function(){e.saveContent(e.vditor.getValue())}},{name:"more",toolbar:["export","preview",{name:"share",tip:"公开",icon:"公开",click:function(){e.publishNote()}}]}]}},mounted:function(){this.init(),this.vditor.clearCache()},created:function(){var e=document.querySelector("#customEditorStyle");if(this.editorConfig.enableCustomStyle&&null!==this.editorConfig.customStylePath&&this.editorConfig.customStylePath.length>5){if(e)return void(e.href=this.editorConfig.customStylePath);var t=document.createElement("link");t.href=this.editorConfig.customStylePath,t.rel="stylesheet",t.id="customEditorStyle",document.querySelector("head").appendChild(t)}else e&&document.querySelector("head").removeChild(e),o.e("chunk-76d4c4d9").then(o.t.bind(null,"5b58",7))},methods:{init:function(){var e=this,t={counter:{enable:this.editorConfig.enableCounter,type:"text"},after:function(){document.querySelector(".vditor-outline__content").classList.add("vditor-toolbar--pin"),document.querySelector(".vditor-outline").classList.add("vditor-toolbar--pin")},input:function(){e.contentModfied=e.vditor.getValue()!==e.originContent},mode:this.editorConfig.editMode,toolbar:this.toolbar,toolbarConfig:{pin:!0},outline:{position:this.editorConfig.outlinePosition,enable:this.editorConfig.enableOutline},value:"",preview:{hljs:{enable:this.editorConfig.enableHighLight,style:this.editorConfig.codeStyle,lineNumber:this.editorConfig.enableLineNumber},maxWidth:4e3},upload:{url:p["a"].HOST_URL+"/file/vditor",headers:this.config.headers}};this.vditor=new k.a("vditor",t)},setContent:function(e,t,o){this.contentModfied=!1,this.notebookName=o,this.showEditTitle=!1,this.vditor.setValue(t),this.originContent=this.vditor.getValue(),this.title=e,this.newTitle=e},clear:function(){this.vditor.setValue(""),this.notebookName=null,this.showEditTitle=!1,this.title=null},setTitleEditable:function(e){this.showEditTitle=e},getContent:function(e){return e||this.vditor.setValue(this.vditor.getValue()),this.vditor.getValue()},getTitle:function(){return this.title},setNotebookName:function(e){this.notebookName=e},publishNote:function(){var e=this,t={notebookName:this.notebookName,noteTitle:this.title};w.a.post(p["a"].HOST_URL+"/article",t,this.config).then((function(t){t=t.data,0===t.code&&(t=t.data,e.$notify({type:"success",message:"公开成功，地址为: "+window.location.host+"/#/article/"+e.config.headers.username+"/"+t.articleId,duration:1e4}))}))},saveContent:function(){if(this.title)if(this.notebookName){this.vditor.setValue(this.vditor.getValue());var e=this.vditor.getValue();this.contentModfied=!1,this.originContent=e,this.$emit("saveContent",e,this.title,this.notebookName)}else this.$notify({type:"warning",message:"请选择笔记本",duration:1e3});else this.$notify({type:"warning",message:"标题为空",duration:1e3})},showHistory:function(){this.$emit("showHistory")},renameTitle:function(){this.$emit("renameTitle",this.newTitle)},handleClose:function(e){this.$confirm("确认关闭？").then((function(){e()})).catch((function(){}))}}},_=y,C=(o("b16a"),Object(l["a"])(_,b,v,!1,null,null,null)),x=C.exports,T=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("div",{staticClass:"history-preview-title"},[e._v("历史版本内容")]),o("div",[o("div",{staticClass:"history-preview-content",staticStyle:{"white-space":"pre-line"}},[e._v(" "+e._s(e.text)+" ")])])])},S=[],R={name:"HistoryPreview",data:function(){return{text:""}},methods:{setText:function(e){this.text=e}},mounted:function(){console.log("dhsifhsiofhods")}},L=R,O=(o("559a"),Object(l["a"])(L,T,S,!1,null,"8a5b01b4",null)),M=O.exports,H=o("f644"),$=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",[o("div",[o("p",{staticClass:"about"},[e._v("开源私有云笔记，基于Git和MarkDown "),o("br"),e._v(" 版本号: "),o("strong",[e._v(e._s(e.version))]),o("br"),e._v(" 项目地址: "),o("a",{attrs:{href:"https://github.com/Hansanshi/mark-idea",target:"_blank"}},[e._v("Hansanshi/mark-idea")])])])])},P=[],V=o("9224"),I={name:"About",data:function(){return{version:V.version}}},U=I,A=(o("796c"),Object(l["a"])(U,$,P,!1,null,"5210c7e4",null)),E=A.exports,D={name:"Home",components:{Editor:x,About:E,HistoryPreview:M},data:function(){return{pageTitle:this.$store.getters.getWebsiteTitle,config:{headers:{token:this.$store.getters.getToken,username:this.$store.getters.getUsername}},isAdminUser:0===this.$store.getters.getUserType,searchNotesName:null,showSearch:!1,searchResult:[],isMobile:!1,keyWord:null,showRenameOption:!1,delNoteList:[],noteRightMenuValues:{},toRenameNotebookName:null,destNotebookName:null,newNoteBookVisible:!1,newNoteVisible:!1,newNotebookName:"",showAboutPage:!1,timerId:null,showHistory:!1,newNoteTitle:"",curNotebook:{notebookName:null,noteList:[]},curNote:{notebookName:null,noteTitle:null,content:""},curNoteVersion:[],curRef:null,showAside:!0,showHistoryPreview:!1,showNotes:!0,notebookList:[],noteList:[]}},methods:{refreshNotebookList:function(e){var t=this;w.a.get(p["a"].HOST_URL+"/note",this.config).then((function(o){if(o=o.data,0===o.code){if(t.notebookList=o.data,e)return void t.doSwitchNotebook(e);t.updateCurNotebookInfo()}})),w.a.get(p["a"].HOST_URL+"/delnote",this.config).then((function(e){e=e.data,0===e.code&&(t.delNoteList=e.data)}))},selectNoteList:function(e){var t=this;this.showSearch&&(this.showSearch=!1,this.showNotes=!0),this.curNotebook.notebookName!==e&&(this.isModifUnsaved()?this.$confirm("修改尚未保存","Confirm",{distinguishCancelAndClose:!0,confirmButtonText:"保存",cancelButtonText:"丢弃"}).then((function(){t.handleSaveContentAndSwitchNotebook(t.$refs.editor.getContent(),e)})).catch((function(o){t.$notify({type:"cancel"===o?"warning":"info",message:"cancel"===o?"丢弃修改":"停留在当前页",duration:1500}),"cancel"===o&&(t.handleDelTmpNote(),t.doSwitchNotebook(e))})):this.doSwitchNotebook(e))},doSwitchNotebook:function(e){var t,o=Object(m["a"])(this.notebookList);try{for(o.s();!(t=o.n()).done;){var n=t.value;if(n.notebookName===e)return this.curNotebook=n,void(n.noteList&&n.noteList.length>0?this.doSwitchNote(n.noteList[0].title,e,!0):this.clearCurNoteInfo())}}catch(i){o.e(i)}finally{o.f()}},updateCurNotebookInfo:function(){var e,t=Object(m["a"])(this.notebookList);try{for(t.s();!(e=t.n()).done;){var o=e.value;o.notebookName===this.curNotebook.notebookName&&(this.curNotebook=o)}}catch(n){t.e(n)}finally{t.f()}},clearCurNoteInfo:function(){this.curNote={content:""},this.$refs.editor.setContent(null,"",null)},clearCurNotebookInfo:function(){this.curNotebook={}},selectNote:function(e,t){var o=this;this.isMobile&&(this.showAside=!1),e===this.curNote.noteTitle&&t===this.curNote.notebookName||(this.isModifUnsaved()?this.$confirm("修改尚未保存","Confirm",{distinguishCancelAndClose:!0,confirmButtonText:"保存",cancelButtonText:"丢弃"}).then((function(){o.saveContentAndSwitchNote(o.$refs.editor.getContent(),t,e)})).catch((function(n){o.$notify({type:"cancel"===n?"warning":"info",message:"cancel"===n?"丢弃修改":"停留在当前页",duration:1500}),"cancel"===n&&(o.handleDelTmpNote(),o.doSwitchNote(e,t))})):this.doSwitchNote(e,t))},doSwitchNote:function(e,t,o){var n=this,i=p["a"].HOST_URL+"/note/"+t+"/"+e;w.a.get(i,this.config).then((function(i){if(i=i.data,0===i.code){n.showHistory=!1;var s={noteTitle:e,content:i.data,notebookName:t};n.curNoteVersion=[],n.curRef=null,n.curNote=s,n.$refs.editor.setContent(e,i.data,t),n.isMobile&&!o&&(n.showAside=!1)}}))},handleSaveContentAndSwitchNotebook:function(e,t){var o=this,n={content:e},i=p["a"].HOST_URL+"/note/"+this.curNote.notebookName+"/"+this.curNote.noteTitle;w.a.post(i,n,this.config).then((function(n){n=n.data,0===n.code&&(o.curNote.content=e,o.refreshNotebookList(t))}))},saveContentAndSwitchNote:function(e,t,o){var n=this,i={content:e},s=p["a"].HOST_URL+"/note/"+this.curNote.notebookName+"/"+this.curNote.noteTitle;w.a.post(s,i,this.config).then((function(e){e=e.data,0===e.code&&(n.doSwitchNote(o,t),n.refreshNotebookList())}))},clearAllDelNotes:function(){var e=this,t=p["a"].HOST_URL+"/delnote";w.a.delete(t,this.config).then((function(t){t=t.data,0===t.code&&(e.delNoteList=[])}))},handleSaveContent:function(e,t,o){var n=this;this.showHistory=!1;var i={content:e},s=p["a"].HOST_URL+"/note/"+o+"/"+t;w.a.post(s,i,this.config).then((function(t){t=t.data,0===t.code&&(n.curNote.content=e,n.refreshNotebookList())}))},handleShowHistory:function(){var e=this;this.showHistory=!this.showHistory;var t=p["a"].HOST_URL+"/note/"+this.curNotebook.notebookName+"/"+this.curNote.noteTitle+"/history";w.a.get(t,this.config).then((function(t){t=t.data,0===t.code&&(e.curNoteVersion=t.data,e.curRef=t.data[0].ref)}))},handleLogout:function(){var e=this,t=p["a"].HOST_URL+"/user/logout";w.a.post(t,null,this.config),this.$store.commit("setLocalInfo",{}),setTimeout((function(){e.$router.push("/login")}),500)},handleCreateNote:function(){this.showHistory=!1,this.curNote.notebookName=this.curNotebook.notebookName,this.curNote.noteTitle=this.newNoteTitle,this.newNoteTitle=void 0,this.curNote.content="",this.$refs.editor.setContent(this.curNote.noteTitle,"",this.curNotebook.notebookName),this.isMobile&&(this.showAside=!1),this.newNoteVisible=!1},handleCreateNotebook:function(){var e=this,t=p["a"].HOST_URL+"/note/"+this.newNotebookName;w.a.put(t,{},this.config).then((function(t){t=t.data,0===t.code&&(e.newNoteBookVisible=!1,e.refreshNotebookList(e.newNotebookName),e.newNotebookName=null)}))},clearRenameInfo:function(){this.toRenameNotebookName=null},clearInfoAndPushToLogin:function(){var e=this;this.$store.commit("setLocalInfo",{}),this.$notify({type:"warning",message:"需登录",duration:1e3}),setTimeout((function(){e.$router.push("/login")}),700)},searchNotes:function(){var e=this;if(this.isModifUnsaved())this.$notify({type:"warning",message:"笔记尚未保存"});else{var t=p["a"].HOST_URL+"/note/search";if(this.keyWord){var o={keyWord:this.keyWord};w.a.post(t,o,this.config).then((function(t){t=t.data,0===t.code&&(e.clearCurNoteInfo(),e.searchNotesName='"'+e.keyWord+'"的搜索结果',e.showSearch=!0,e.showNotes=!1,e.searchResult=t.data)}))}else this.$notify({type:"warning",message:"搜索关键词不可为空"})}},validateUserAndInit:function(){var e=this;if(this.config.headers.token){var t=p["a"].HOST_URL+"/user/validate";w.a.post(t,null,this.config).then((function(t){t=t.data,0!==t.code?e.clearInfoAndPushToLogin():e.doInit()}))}else this.clearInfoAndPushToLogin()},handleNoteRightMenu:function(e){this.noteRightMenuValues.noteTitle=e.data.key,this.noteRightMenuValues.notebookName=this.curNotebook.notebookName,this.showRenameOption=this.isCurNote()},handleNotebookRightMenu:function(e){this.notebookRightMenuValues={notebookName:e.data.key}},handleDelNoteRightMenu:function(e){this.delNoteRightMenuValues={delNoteId:e.data.key}},clearDelNote:function(){var e=this,t=this.delNoteRightMenuValues.delNoteId,o=p["a"].HOST_URL+"/delnote/"+t;w.a.delete(o,this.config).then((function(t){t=t.data,0===t.code&&w.a.get(p["a"].HOST_URL+"/delnote",e.config).then((function(t){t=t.data,0===t.code&&(e.delNoteList=t.data)}))}))},recoverDelNote:function(){var e=this,t=this.delNoteRightMenuValues.delNoteId,o=p["a"].HOST_URL+"/delnote/"+t+"?recover=true";w.a.delete(o,this.config).then((function(t){t=t.data,0===t.code&&e.refreshNotebookList()}))},handleDelNotebook:function(){var e=this,t=p["a"].HOST_URL+"/note/"+this.notebookRightMenuValues.notebookName;w.a.delete(t,this.config).then((function(t){t=t.data,0===t.code&&(e.clearCurNotebookInfo(),e.clearCurNoteInfo(),e.refreshNotebookList())}))},allowRenameNotebook:function(){this.toRenameNotebookName=this.notebookRightMenuValues.notebookName,this.destNotebookName=this.toRenameNotebookName},handleRenameNotebook:function(){var e=this,t=p["a"].HOST_URL+"/note/"+this.destNotebookName,o={move:!0,srcNotebook:this.toRenameNotebookName};this.clearRenameInfo(),this.showHistory=!1,w.a.put(t,o,this.config).then((function(t){t=t.data,0===t.code?(t=t.data,o.srcNotebook===e.curNote.notebookName&&e.$refs.editor.setNotebookName(e.destNotebookName),e.notebookList.forEach((function(n){n.notebookName===o.srcNotebook&&(n.notebookName=e.destNotebookName,n.noteList=t)}))):e.$notify({type:"error",message:t.msg,duration:1e3})}))},isCurNote:function(){return this.noteRightMenuValues.notebookName===this.curNotebook.notebookName&&this.noteRightMenuValues.noteTitle===this.curNote.noteTitle},handleDelNote:function(){var e=this,t=p["a"].HOST_URL+"/note/"+this.noteRightMenuValues.notebookName+"/"+this.noteRightMenuValues.noteTitle;w.a.delete(t,this.config).then((function(t){t=t.data,0===t.code&&e.refreshNotebookList(e.noteRightMenuValues.notebookName)}))},handleDelTmpNote:function(){var e=p["a"].HOST_URL+"/note/"+this.curNote.notebookName+"/"+this.curNote.noteTitle+"?delDraft=true";w.a.delete(e,this.config)},handleRenameNote:function(){this.$refs.editor.setTitleEditable(!0)},doHandleRenameTitle:function(e){var t=this,o=this.curNotebook.notebookName,n=p["a"].HOST_URL+"/note/"+o+"/"+e,i={srcNotebook:o,srcTitle:this.curNote.noteTitle,move:!0};w.a.put(n,i,this.config).then((function(e){e=e.data,0===e.code?t.refreshNotebookList(t.curNotebook.notebookName):t.$notify({type:"warning",message:e.msg})}))},handleMoveNote:function(e){var t=this,o=e,n=p["a"].HOST_URL+"/note/"+o+"/"+this.noteRightMenuValues.noteTitle,i={srcNotebook:this.noteRightMenuValues.notebookName,srcTitle:this.noteRightMenuValues.noteTitle,move:!0};w.a.put(n,i,this.config).then((function(e){e=e.data,0===e.code?t.refreshNotebookList(t.curNotebook.notebookName):t.$notify({type:"warning",message:e.msg})}))},handleCopyNote:function(e){var t=this,o=e,n=p["a"].HOST_URL+"/note/"+o+"/"+this.noteRightMenuValues.noteTitle,i={srcNotebook:this.noteRightMenuValues.notebookName,srcTitle:this.noteRightMenuValues.noteTitle};w.a.put(n,i,this.config).then((function(e){e=e.data,0===e.code?t.refreshNotebookList(t.curNotebook.notebookName):t.$notify({type:"warning",message:e.msg})}))},handleRecover:function(e){var t=this,o=this.curNotebook.notebookName,n=this.curNote.noteTitle,i=p["a"].HOST_URL+"/note/"+o+"/"+n,s={versionRef:e};w.a.post(i,s,this.config).then((function(e){if(e=e.data,0===e.code){if(n!==t.curNote.noteTitle)return;t.curNote.content=e.data,t.$refs.editor.setContent(n,e.data,o),t.handleShowHistory(),t.refreshNotebookList()}}))},handlePreviewHistory:function(e){this.showHistoryPreview=!0,this.previewRef=e},handleOpenHistoryPrev:function(){var e=this;this.$refs.historyPreview.setText("fdshiufhsiusdh745897384957489357");var t=p["a"].HOST_URL+"/history/queryHistoryContent",o={notebookName:this.curNotebook.notebookName,noteTitle:this.curNote.noteTitle,versionRef:this.previewRef};w.a.post(t,o,this.config).then((function(t){t=t.data,0===t.code?e.$refs.historyPreview.setText(t.data):e.$notify({type:"error",message:"获取历史版本内容失败",duration:1e3})}))},doInit:function(){var e=this;this.refreshNotebookList(),this.timerId=setInterval((function(){e.autoSaveNote()}),1e4),this.setIsMobile(),window.onresize=this.setIsMobile},setIsMobile:function(){this.isMobile=H["a"].isMobile()},isModifUnsaved:function(e){return(10!==this.$refs.editor.getContent(e).charCodeAt()||""!==this.curNote.content)&&this.curNote.content!==this.$refs.editor.getContent(e)},autoSaveNote:function(){var e=this;if(this.isModifUnsaved(!0)&&this.$refs.editor.getContent(!0)!==this.curNote.tmpContent&&this.curNote&&this.curNote.noteTitle&&0!==this.curNote.noteTitle.length){var t=this.$refs.editor.getContent(!0),o={content:t,tmpSave:!0},n=p["a"].HOST_URL+"/note/"+this.curNote.notebookName+"/"+this.curNote.noteTitle;w.a.post(n,o,this.config).then((function(o){o=o.data,0===o.code&&(e.curNote.tmpContent=t)}))}}},mounted:function(){document.title=this.$store.getters.getWebsiteTitle,this.validateUserAndInit()},beforeRouteLeave:function(e,t,o){clearInterval(this.timerId),o()}},j=D,z=(o("cccb"),Object(l["a"])(j,d,f,!1,null,null,null)),W=z.exports;n["default"].use(h["a"]);var q=[{path:"/",name:"Home",component:W},{path:"/login",name:"login",component:function(){return o.e("chunk-71367435").then(o.bind(null,"a55b"))}},{path:"/setting",name:"setting",component:function(){return o.e("chunk-0d9ba75c").then(o.bind(null,"4ef5"))}},{path:"/admin",name:"admin",component:function(){return o.e("chunk-2d0b9d35").then(o.bind(null,"3530"))}},{path:"/article/:author/:id",name:"article",props:!0,component:function(){return Promise.all([o.e("chunk-76d4c4d9"),o.e("chunk-7b8aceec")]).then(o.bind(null,"3ad6"))}}],B=new h["a"]({base:"/",routes:q}),J=B,K=o("2f62");n["default"].use(K["a"]);var F={};localStorage.localInfo&&(F=JSON.parse(localStorage.localInfo));var G=new K["a"].Store({state:{info:F,website:{}},mutations:{setWebsiteTitle:function(e,t){e.website.title=t},setToken:function(e,t){e.info.token=t,localStorage.localInfo=JSON.stringify(F)},setEditorConfig:function(e,t){e.info.editorConfig=t,localStorage.localInfo=JSON.stringify(F)},setLocalInfo:function(e,t){e.info=t,localStorage.localInfo=JSON.stringify(t)}},getters:{getToken:function(e){return e.info.token},getUsername:function(e){return e.info.username},getUserType:function(e){return e.info.type},getEditorConfig:function(e){return e.info.editorConfig},getWebsiteTitle:function(e){return e.website.title}},actions:{},modules:{}}),Q=o("5c96"),X=o.n(Q),Y=(o("8aa1"),o("d625"));o("d50b");n["default"].config.productionTip=!1,n["default"].use(X.a),n["default"].use(Y["a"]),new n["default"]({router:J,store:G,render:function(e){return e(u)}}).$mount("#app")},"5ced":function(e,t,o){},"796c":function(e,t,o){"use strict";var n=o("bb9e"),i=o.n(n);i.a},"8aa1":function(e,t,o){},9224:function(e){e.exports=JSON.parse('{"name":"mark-idea-front","version":"0.4.7","private":true,"scripts":{"serve":"vue-cli-service serve","build":"vue-cli-service build","lint":"vue-cli-service lint"},"dependencies":{"axios":"^0.21.2","core-js":"^3.6.5","element-ui":"^2.13.2","v-contextmenu":"^2.9.0","vditor":"^3.8.4","vue":"^2.6.11","vue-router":"^3.2.0","vue-runtime-helpers":"^1.1.2","vuex":"^3.4.0"},"devDependencies":{"@vue/cli-plugin-babel":"^4.4.0","@vue/cli-plugin-eslint":"^4.4.0","@vue/cli-service":"^4.4.0","babel-eslint":"^10.1.0","eslint":"^6.7.2","eslint-plugin-vue":"^6.2.2","vue-template-compiler":"^2.6.11"}}')},a9de:function(e,t,o){},b16a:function(e,t,o){"use strict";var n=o("25f7"),i=o.n(n);i.a},bb9e:function(e,t,o){},cccb:function(e,t,o){"use strict";var n=o("5ced"),i=o.n(n);i.a},d50b:function(e,t,o){},f644:function(e,t,o){"use strict";function n(){return window.innerWidth<551}t["a"]={isMobile:n}}});
//# sourceMappingURL=app.6d5cc521.js.map