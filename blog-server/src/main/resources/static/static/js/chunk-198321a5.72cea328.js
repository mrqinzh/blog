(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-198321a5"],{"090c":function(e,t,i){},"0a6c":function(e,t,i){"use strict";i.r(t);var l=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"email-container"},[i("el-form",{ref:"emailForm",staticClass:"email-form",attrs:{model:e.emailForm,"label-width":"100px"}},[i("el-form-item",{attrs:{label:"邮件主题",prop:"emailTitle",rules:[{required:!0,message:"邮件主题不能为空",trigger:"blur"}]}},[i("el-input",{model:{value:e.emailForm.emailTitle,callback:function(t){e.$set(e.emailForm,"emailTitle",t)},expression:"emailForm.emailTitle"}})],1),i("el-form-item",{attrs:{prop:"to",label:"收件人",rules:[{required:!0,message:"请输入邮箱地址",trigger:"blur"},{type:"email",message:"请输入正确的邮箱地址",trigger:["blur","change"]}]}},[i("el-input",{model:{value:e.emailForm.to,callback:function(t){e.$set(e.emailForm,"to",t)},expression:"emailForm.to"}})],1),i("el-form-item",{attrs:{label:"邮件内容"}},[i("el-input",{attrs:{type:"textarea"},model:{value:e.emailForm.emailContent,callback:function(t){e.$set(e.emailForm,"emailContent",t)},expression:"emailForm.emailContent"}})],1),i("el-form-item",[i("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("emailForm")}}},[e._v("提交")]),i("el-button",{on:{click:e.resetForm}},[e._v("重置")])],1)],1)],1)},a=[],r=i("b775");function m(e){return Object(r["a"])({url:"/email/simple",method:"post",data:e})}var o={data:function(){return{emailForm:{emailTitle:"",to:"",emailContent:""}}},methods:{submitForm:function(e){var t=this;""!=this.emailForm.emailContent?this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;alert("submit!"),m(t.emailForm).then((function(e){e.success&&(t.$message.success("邮件发送成功了"),t.resetForm())}))})):this.$message.warning("请填写邮件内容")},resetForm:function(){this.$refs["emailForm"].resetFields(),this.emailForm.emailTitle="",this.emailForm.to="",this.emailForm.emailContent=""}}},s=o,n=(i("ab34"),i("cba8")),u=Object(n["a"])(s,l,a,!1,null,"f49ca112",null);t["default"]=u.exports},ab34:function(e,t,i){"use strict";i("090c")}}]);