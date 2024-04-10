<template>
  <div>
  <div style="margin-left: 50px;margin-top: 50px">
  <el-descriptions title="个人信息" :column="1"  border style="width: 600px">
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        用户编号
      </template>
      {{this.userForm.userID}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-s-custom"></i>
        用户名
      </template>
      {{this.userForm.userName}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-edit"></i>
        用户昵称
      </template>
        {{this.userForm.name}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-coin"></i>
        年龄
      </template>
      {{this.userForm.age}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-male"></i>
        性别
      </template>
      {{this.userForm.sex}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        联系方式
      </template>
      {{this.userForm.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-message"></i>
        电子邮件
      </template>
      {{this.userForm.email}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-place"></i>
        用户类型
      </template>
      <span v-if="this.userForm.roleID===0">普通用户</span>
      <span v-if="this.userForm.roleID===1">管理员</span>
    </el-descriptions-item>
  </el-descriptions>
    <el-button type="primary" style="margin-top: 15px" @click="update">修改个人资料</el-button>

    <el-dialog title="修改个人资料" :visible.sync="userFormVisible" width="400px">
      <el-form ref="userForm" :model="userForm"  label-width="80px" :rules="rules">
        <el-form-item label="用户昵称" prop="name">
          <el-input v-model="userForm.name" ></el-input>
        </el-form-item>
        <el-form-item label="用户年龄" prop="age">
          <el-input v-model="userForm.age" ></el-input>
        </el-form-item>
        <el-form-item label="用户性别" prop="sex">
          <el-input v-model="userForm.sex" ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="userForm.phone" ></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="userForm.email" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateInformation">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  </div>
</template>

<script>
export default {
  name: "UserHome",
  data() {
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
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      userFormVisible: false,
      userForm: {
        userID:'',
        userName:'',
        password:'',
        name:'',
        age:'',
        sex:'',
        phone:'',
        email:'',
        roleID:'',
        statue:''
      },
      rules: {
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
      }
    }
  },
  methods:{
    update(){
      this.userFormVisible=true
    },
    updateInformation(){
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8081/user/update', this.userForm).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改个人资料成功',
                type: 'success'
              });
              this.$axios.get("http://localhost:8081/user/select?userName="+this.userForm.userName).then(res => res.data).then(res => {
                sessionStorage.setItem('CurUser',JSON.stringify(res.data))
              })
              this.userFormVisible=false
            } else {
              this.$message({
                showClose: true,
                message: '修改个人资料失败',
                type: 'error'
              });
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写个人资料',
            type: 'error'
          });
        }
      });
    },
  },
  beforeMount() {
    this.userForm.userID=this.user.userID
    this.userForm.userName=this.user.userName
    this.userForm.password=this.user.password
    this.userForm.name=this.user.name
    this.userForm.age=this.user.age
    this.userForm.sex=this.user.sex
    this.userForm.phone=this.user.phone
    this.userForm.email=this.user.email
    this.userForm.roleID=this.user.roleID
    this.userForm.statue=this.user.statue
  }
}
</script>

<style scoped>

</style>