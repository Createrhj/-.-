<template>
  <div class="base">
    <!-- 注册登录界面 -->
    <div  class="loginAndRegister">
      <!--登录表单-->
      <div  class="loginArea">
        <!-- 标语 -->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="isShow" class="title">
            LOGIN
          </div>
        </transition>
        <!-- 密码框和用户名框 -->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="isShow" class="pwdArea">
            <div style="flex: 1;justify-content: center;display: flex;align-items: center">
              <el-form class="LoginForm" ref="LoginForm" :model="LoginForm" :rules="LoginRule" label-width="80px" >
                <el-form-item label="用户名" prop="userName" style="margin-bottom: 20px;width: 235px">
                  <el-input v-model="LoginForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" style="margin-top: 20px;width: 235px">
                  <el-input type="password" v-model="LoginForm.password"
                            show-password autocomplete="off" @keyup.e.enter.native="LoginConfirm"></el-input>
                </el-form-item>
                <el-button type="text" style="margin-left: 160px;font-size: 15px" @click="updatePasswordDialog=true">忘记密码?</el-button>
              </el-form>
            </div>

          </div>
        </transition>
        <!-- 登录按钮 -->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="isShow" class="btnArea">
            <el-button type="success" round style="background-color: #257B5E;margin-bottom: 70px;letter-spacing: 5px;font-size: 15px"
                       @click="LoginConfirm"
                       :disabled="confirm_disabled">登录</el-button>
          </div>
        </transition>
      </div>

      <el-dialog title="修改用户密码" :visible.sync="updatePasswordDialog" width="375px">
        <el-form ref="UserForm" :model="UserForm" :rules="userRule" style="margin-left: 40px">
          <div v-if="active===0">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="UserForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="联系方式" prop="phone">
            <el-input v-model="UserForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="电子邮件" prop="email">
            <el-input v-model="UserForm.email"></el-input>
          </el-form-item>
            <el-button type="primary" style="margin-top: 12px;" @click="verifyUser">用户校验</el-button>
          </div>
          <div v-if="active===1">
          <el-form-item label="请输入新密码" prop="password">
            <el-input type="password" v-model="UserForm.password" ></el-input>
          </el-form-item>
            <el-form-item label="请再次输入新密码" prop="password02">
              <el-input type="password" v-model="UserForm.password02"></el-input>
            </el-form-item>
            <el-button type="primary" style="margin-top: 12px;" @click="updatePassword">修改密码</el-button>
          </div>
          <div v-if="active===2">
            <el-row>
            <el-col :sm="20" :lg="30">
              <el-result icon="success" title="修改密码成功" subTitle="请返回主页面进行登录">
                <template slot="extra">
                  <el-button type="primary" size="medium" @click="GoLogin">返回主页</el-button>
                </template>
              </el-result>
            </el-col>
            </el-row>
          </div>
        </el-form>
        <div style="margin-top: 50px">
          <el-steps :active="this.active" finish-status="success" simple style="margin-top: 20px">
            <el-step title="用户校验" ></el-step>
            <el-step title="修改密码" ></el-step>
            <el-step title="返回登录" style="width: 60px"></el-step>
          </el-steps>
        </div>
      </el-dialog>

      <!-- 注册表单 -->
      <div class="registerArea">
        <!--注册表头-->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="!isShow" class="registerTitle">
            用户注册
          </div>
        </transition>
        <!--注册表单-->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div  v-show="!isShow" class="registerForm">
            <div style="flex: 1;display: flex;justify-content: center;align-items: center">
              <el-form class="RegisterForm" ref="RegisterForm" :model="RegisterForm" :rules="RegisterRule" label-width="80px">
                <el-form-item label="用 户 名" prop="userName" class="theLabel">
                  <el-input v-model="RegisterForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                  <el-input type="password" v-model="RegisterForm.password"></el-input>
                </el-form-item>
                <el-form-item label="昵 称" prop="name">
                  <el-input v-model="RegisterForm.name"></el-input>
                </el-form-item>
                <el-form-item label="年 龄" prop="age">
                  <el-input v-model="RegisterForm.age"></el-input>
                </el-form-item>
                <el-form-item label="性 别" prop="sex">
                  <el-input v-model="RegisterForm.sex"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="phone">
                  <el-input v-model="RegisterForm.phone"></el-input>
                </el-form-item>
                <el-form-item label="电子邮件" prop="email">
                  <el-input v-model="RegisterForm.email"></el-input>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </transition>
        <!--注册按钮-->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div  v-show="!isShow" class="registerBtn">
            <el-button type="success" round style="background-color: #257B5E;letter-spacing: 5px" @click="RegisterConfirm">注册</el-button>
            <el-button type="success" round style="background-color: #42b983;letter-spacing: 5px" @click="resetForm">重置</el-button>
          </div>
        </transition>
      </div>
      <!-- 信息展示界面 -->
      <div id="aaa" class="showInfo"
           :style="{
             borderTopRightRadius:styleObj.bordertoprightradius,
             borderBottomRightRadius:styleObj.borderbottomrightradius,
             borderTopLeftRadius:styleObj.bordertopleftradius,
             borderBottomLeftRadius:styleObj.borderbottomleftradius,
             right:styleObj.rightDis
            }"
           ref="showInfoView">
        <!-- 没有用户输入用户名或者找不到用户名的时候 -->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="isShow" style="display: flex;flex-direction: column;align-items: center;justify-content: center;width: 100%;height: 100%">
            <!-- 欢迎语 -->
            <div style="flex: 2;display: flex;align-items: center;font-size: 22px;color: #FFFFFF;font-weight: bold">
              欢迎登入花茶系统
            </div>
            <!-- 欢迎图片 -->
            <div style="flex: 2">
              <el-button type="success"  style="background-color:#257B5E;border: 1px solid #ffffff;" round @click="changeToRegister">还没有账户？点击注册</el-button>
            </div>
          </div>
        </transition>
        <!-- 用户注册的时候展示信息 -->
        <transition
            name="animate__animated animate__bounce"
            enter-active-class="animate__fadeInUp"
            leave-active-class="animate__zoomOut"
            appear
        >
          <div v-show="!isShow" style="display: flex;flex-direction: column;align-items: center;justify-content: center;width: 100%;height: 100%">
            <!-- 欢迎语 -->
            <div style="flex: 2;display: flex;align-items: center;font-size: 30px;color: #FFFFFF;font-weight: bold">
              欢迎注册
            </div>
            <!-- 欢迎图片 -->
            <div style="flex: 2">
              <el-button type="success"  style="background-color:#257B5E;border: 1px solid #ffffff;" round @click="changeToLogin">已有账户？点击登录</el-button>
            </div>
          </div>
        </transition>
      </div>

    </div>

  </div>
</template>

<script>
import 'animate.css'
export default {

  name:'AloHin',
  data(){
    let checkPwd=(rule,value,callback)=>{
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.UserForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    let checkSex = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入性别'));
      }
      else if(value ==='男' || value ==='女') {
        callback();
      }
      else{
        callback(new Error('请正确输入性别'));
      }
    };
    let checkAge = (rule, value, callback) => {
      if(value === '') {
        callback(new Error('请输入年龄'));
      }
      else if(value >100) {
        callback(new Error('年龄范围1~100'));
      }
      else{
        callback();
      }
    };
    let checkPhone = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入联系方式'));
      }else if(value.length !== 11) {
        callback(new Error('联系方式需要11位数字'));
      }
      else{
        callback();
      }
    };
    let checkUserName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      }
      else if(value.length>15) {
        callback(new Error('用户名范围1~15'));
      }
      else{
        callback();
      }
    };
    let checkName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户昵称'));
      }
      else if(value.length>6) {
        callback(new Error('用户昵称范围1~6'));
      }
      else{
        callback();
      }
    };
    return{
      confirm_disabled:false,
      updatePasswordDialog:false,
      active: 0,
      UserForm:{
        userName:'',
        password:'',
        password02:'',
        phone:'',
        email:''
      },
      LoginForm:{
        userName:'',
        password:'',
      },
      RegisterForm:{
        userName:'',
        password:'',
        name:'',
        age:'',
        sex:'',
        phone:'',
        email:'',
        statue:1
      },
      LoginRule: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请选输入密码', trigger: 'blur' }
        ]
      },
      RegisterRule: {
        userName: [
          {validator: checkUserName, required: true, trigger: "blur" }
        ],
        password: [
          { required: true, message: '请选输入密码', trigger: 'blur' }
        ],
        name: [
          {validator: checkName, required: true, trigger: "blur" }
        ],
        age: [
          {validator: checkAge, required: true, trigger: "blur" }
        ],
        sex: [
          {validator: checkSex, required: true, trigger: "blur" }
        ],
        phone: [
          {validator: checkPhone, required: true, trigger: "blur" }
        ],
        email: [
          { required: true, message: '请选输入电子邮件', trigger: 'blur' }
        ],
      },
      userRule:{
        userName: [{required: true, message: "请输入用户名", trigger: "blur" }],
        phone: [{required: true, message: "请输入联系方式", trigger: "blur" }],
        email: [{required: true, message: "请输入电子邮件", trigger: "blur" }],
        password: [{required: true, message: "请输入新密码", trigger: "blur" }],
        password02: [{validator: checkPwd, required: true, trigger: "blur" }],
      },

      styleObj:{
        bordertoprightradius:'15px',
        borderbottomrightradius: '15px',
        bordertopleftradius:'0px',
        borderbottomleftradius:'0px',
        rightDis:'0px'
      },
      isShow:true
    }
  },
  methods:{
    changeToRegister(){
      this.styleObj.bordertoprightradius= '0px'
      this.styleObj.borderbottomrightradius='0px'
      this.styleObj.bordertopleftradius='15px'
      this.styleObj.borderbottomleftradius='15px'
      this.styleObj.rightDis='50%'
      this.isShow = !this.isShow
    },
    changeToLogin(){
      this.styleObj.bordertoprightradius= '15px'
      this.styleObj.borderbottomrightradius='15px'
      this.styleObj.bordertopleftradius='0px'
      this.styleObj.borderbottomleftradius='0px'
      this.styleObj.rightDis='0px'
      this.isShow = !this.isShow
    },
    LoginConfirm(){
      this.confirm_disabled=true;
      this.$refs.LoginForm.validate((valid)=>{
        if(valid){
          this.$axios.post('http://localhost:8081/user/login',this.LoginForm).then(res=>res.data).then(res=>{
            if(res.code===888){
              if(res.data.user.statue===1) {
                sessionStorage.setItem("CurUser", JSON.stringify(res.data.user))
                this.$router.replace('/Manager/Homepage');
                this.$message({
                  showClose: true,
                  message: '用户登录成功',
                  type: 'success'
                });
              }else {
                this.$message({
                  showClose: true,
                  message: '该用户已被停用',
                  type: 'warning'
                });
              }
            }else {
              this.confirm_disabled=false;
              this.$message({
                showClose: true,
                message: '用户或者密码错误',
                type: 'error'
              });
              return false;
            }
          });
        }else {
          this.confirm_disabled=false;
          this.$message({
            showClose: true,
            message: '请完整填写信息',
            type: 'warning'
          });
          return false;
        }
      })
    },
    RegisterConfirm(){
      this.$refs.RegisterForm.validate((valid)=>{
        if(valid){
          this.$axios.get("http://localhost:8081/user/select?userName="+this.RegisterForm.userName).then(res=>res.data).then(res=>{
            if(res.code===888){
              this.$message({
                showClose: true,
                message: '用户已经存在,请修改用户名',
                type: 'error'
              });
            }else{
              this.$axios.post('http://localhost:8081/user/save',this.RegisterForm).then(res=>res.data).then(res=>{
                if(res.code===888){
                  this.$message({
                    showClose: true,
                    message: '用户注册成功',
                    type: 'success'
                  });
                  this.changeToLogin()
                  this.$refs.RegisterForm.resetFields()
                }else {
                  this.$message({
                    showClose: true,
                    message: '用户注册失败',
                    type: 'error'
                  });
                  return false;
                }
              });
            }
          })
        }else {
          this.$message({
            showClose: true,
            message: '请完整填写信息',
            type: 'warning'
          });
          return false;
        }
      })
    },
    verifyUser(){
      this.$axios.post('http://localhost:8081/user/checkUser',this.UserForm).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '用户校验成功',
            type: 'success'
          });
          this.next()
        }else{
              this.$message({
                showClose: true,
                message: '用户校验失败',
                type: 'error'
              });
            }
        })
    },
    updatePassword(){
      this.$refs.UserForm.validate((valid)=>{
        if(valid){
          this.$axios.post('http://localhost:8081/user/updatePassword',this.UserForm).then(res=>res.data).then(res=>{
            if(res.code===888){
              this.$message({
                showClose: true,
                message: '修改密码成功',
                type: 'success'
              });
              this.next()
            }else{
              this.$message({
                showClose: true,
                message: '修改密码失败',
                type: 'error'
              });
            }
          })
        }else {
          this.$message({
            showClose: true,
            message: '修改密码失败',
            type: 'warning'
          });
        }
      })
    },
    GoLogin(){
      this.updatePasswordDialog=false
      this.UserForm={};
      this.next()
    },
    next() {
      if (this.active++ > 1) this.active = 0;
    },
    resetForm(){
        this.$refs.RegisterForm.resetFields();
    },
  },

}
</script>

<style>
.el-form-item{
  margin-bottom: 10px;
  width: 225px;
}
.base{
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/images/background.png");
  background-size: 100%;
}
.loginAndRegister{
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.loginArea{
  background-color: rgba(255,255,255,0.8);
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  height: 500px;
  width: 400px;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}
.registerArea{
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  height: 500px;
  width: 400px;
  background-color: rgba(255,255,255,0.8);
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content:center ;
  align-items: center;
}
.showInfo{
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  position: absolute;
  height: 500px;
  width: 400px;
  z-index:2;
  top: 0;
  right: 0;
  background-image: url("../assets/images/welcome.png");
  background-size: 90%;
}
.showInfo:hover{
  background-size: 100%;
  background-position: -50px -50px;
}
.title{
  width: 70%;
  flex:1;
  border-bottom: 1px solid #257B5E;
  display: flex;
  align-items: center;
  color: #257B5E;
  font-weight: bold;
  font-size: 50px;
  display: flex;
  justify-content: center;
}
#aaa{
  transition: 0.3s linear;
}
.pwdArea{
  width: 100%;
  flex:2;
  display: flex;
  flex-direction: column;
  display: flex;
  flex-direction: column;
}
.pwdArea input{
  outline: none;
  height: 40%;
  border-radius:10px ;
  padding-left: 10px;
  font-size: 20px;
  border: 1px solid gray;
}
.pwdArea input:focus{
  border: 2px solid #257B5E;
}

.btnArea{
  flex:1;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.registerTitle{
  width: 70%;
  flex: 1;
  color: #257B5E;
  font-weight: bold;
  font-size: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid #257B5E;
}
.registerForm{
  flex: 2;
  display: flex;
  flex-direction: column;
  color: #257B5E;
  font-size: 30px;
}
.registerForm input{
  outline: none;
  height: 15%;
  border-radius:20px ;
  padding-left: 10px;
  font-size: 12px;
  border: 2px solid gray;
}
.registerForm input:focus{
  border: 2px solid #257B5E;
}
.registerBtn{
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>