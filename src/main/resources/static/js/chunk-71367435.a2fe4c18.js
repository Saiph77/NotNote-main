(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-71367435"],{a55b:function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticStyle:{"margin-top":"40px"}},[a("el-card",{staticClass:"box-card",staticStyle:{"background-color":"rgb(253, 253, 253)"}},[a("div",{attrs:{align:"center"}},[a("div",{staticClass:"markideaname",staticStyle:{"font-size":"40px"}},[e._v(" "),a("strong",[e._v(e._s(e.pageTitle))])])]),a("div",{staticClass:"box-img",attrs:{align:"center"}},[e._v(" 📕 ")]),a("el-form",{attrs:{model:e.formData},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleLogin(t)}}},[a("el-form-item",{attrs:{prop:"username",rules:e.formRules.username}},[a("el-input",{staticClass:"box-input",attrs:{maxlength:"10",placeholder:"用户名"},model:{value:e.formData.username,callback:function(t){e.$set(e.formData,"username",t)},expression:"formData.username"}})],1),a("el-form-item",{attrs:{prop:"password",rules:e.formRules.password}},[a("el-input",{staticClass:"box-input",attrs:{type:"password",placeholder:"密码"},model:{value:e.formData.password,callback:function(t){e.$set(e.formData,"password",t)},expression:"formData.password"}})],1),a("div",{staticClass:"box-btn"},[a("el-button",{attrs:{type:"primary"},on:{click:e.handleLogin}},[e._v("登录")]),a("el-button",{on:{click:e.handleRegister}},[e._v("注册")])],1)],1)],1)],1)},n=[],r=a("bc3a"),o=a.n(r),i=a("2919"),l={name:"login",data:function(){return{pageTitle:this.$store.getters.getWebsiteTitle,formData:{username:null,password:null},formRules:{username:[{required:!0,message:"不能为空"},{pattern:/^[A-Za-z0-9]+$/,message:"参数非法"}],password:[{required:!0,message:"不能为空"},{pattern:/^[A-Za-z0-9!@#$^]+$/,message:"参数非法"}]}}},methods:{handleLogin:function(){var e=this,t={username:this.formData.username,password:this.formData.password};o.a.post(i["a"].HOST_URL+"/user/login",t).then((function(t){t=t.data,0===t.code?(console.log(t.data),e.$notify({type:"success",message:"登录成功",duration:1e3}),e.$store.commit("setLocalInfo",t.data),setTimeout((function(){e.$router.push("/")}),700)):e.$notify({type:"warning",message:"用户名或密码错误",duration:2e3})}))},handleRegister:function(){var e=this,t={username:this.formData.username,password:this.formData.password},a=i["a"].HOST_URL+"/user/register";o.a.post(a,t).then((function(t){t=t.data,0===t.code?e.$notify({type:"success",message:"注册成功"}):e.$notify({type:"warning",message:t.msg,duration:700})}))}},mounted:function(){document.title=this.$store.getters.getWebsiteTitle+" - 登录"}},u=l,c=(a("d6db"),a("2877")),m=Object(c["a"])(u,s,n,!1,null,null,null);t["default"]=m.exports},d6db:function(e,t,a){"use strict";var s=a("e67a"),n=a.n(s);n.a},e67a:function(e,t,a){}}]);
//# sourceMappingURL=chunk-71367435.a2fe4c18.js.map