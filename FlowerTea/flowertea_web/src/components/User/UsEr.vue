<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input
          v-model="userName"
          placeholder="请输入用户名称"
          style="width: 200px"
          suffix-icon="el-icon-search"></el-input>
      <el-select v-model="roleID" clearable placeholder="请选择" style="margin-left:10px;width: 100px">
        <el-option
            v-for="item in roleIDs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
      <el-button type="primary" @click="addUser">新增<i class="el-icon-upload el-icon--right"></i></el-button>
    </div>
    <!--用户信息表-->
    <el-table :data="tableData"
              :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
              border
              style="overflow: auto;height: 506px;margin-bottom: 10px"
    >
      <el-table-column type="expand">
        <template v-slot="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用户ID">
              <span>{{ props.row.userID }}</span>
            </el-form-item>
            <el-form-item label="用户昵称">
              <span>{{ props.row.name}}</span>
            </el-form-item>
            <el-form-item label="用户名">
              <span>{{ props.row.userName }}</span>
            </el-form-item>
            <el-form-item label="用户密码">
              <span>{{ props.row.password }}</span>
            </el-form-item>
            <el-form-item label="用户年龄">
              <span>{{ props.row.age }}</span>
            </el-form-item>
            <el-form-item label="用户性别">
              <span>{{ props.row.sex }}</span>
            </el-form-item>
            <el-form-item label="联系方式">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label="电子邮件">
              <span>{{ props.row.email }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          type="index"
          label="序号"
          width="80px">
      </el-table-column>

      <el-table-column prop="userName" label="用户名" width="100">
      </el-table-column>

      <el-table-column prop="name" label="昵称" width="200">
      </el-table-column>

      <el-table-column prop="roleID" label="用户权限" width="160">
        <template v-slot="scope">
          <div class="icon-size" style="font-size: 25px">
          <i class="el-icon-s-custom" v-if="scope.row.roleID===1">
            <el-tag type="danger" style="margin: 5px">管理员</el-tag>
          </i>
          <i class="el-icon-user-solid" v-if="scope.row.roleID===0">
            <el-tag style="margin:5px">用户</el-tag>
          </i>
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="statue" label="用户状态" width="100">
        <template v-slot="scope">
          <div>
              <el-tag type="danger" style="margin: 5px" v-if="scope.row.statue===0">禁用</el-tag>
              <el-tag style="margin:5px" v-if="scope.row.statue===1">启用</el-tag>
          </div>
          <div style="margin-left: 8px">
          <el-switch
              v-model="scope.row.statue"
              :active-value="1"
              :inactive-value="0"
              @change="changeStatue(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949"
              >
          </el-switch>
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="200">
        <template v-slot="scope">
          <el-button type="success" @click="update(scope.row)" style="margin-right: 10px">编辑</el-button>
          <el-popconfirm
              title="确定删除该用户信息吗？"
              @confirm="del(scope.row)"
          >
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--添加用户信息-->
    <el-dialog title="添加用户信息"
        :visible.sync="addDialog"
        width="30%"
        center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-input v-model="form.sex"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="用户权限" prop="roleID">
          <el-input v-model="form.roleID"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" prop="statue">
          <el-input v-model="form.statue"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveUser">创建用户</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
    </el-dialog>
    <!--修改用户信息-->
    <el-dialog title="修改用户信息"
        :visible.sync="updateDialog"
        width="30%"
        center
        >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-input v-model="form.sex"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="用户权限" prop="roleID">
          <el-input v-model="form.roleID"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" prop="statue">
          <el-input v-model="form.statue"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUser">修改</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
    </el-dialog>
    <!--分页栏-->
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
    >
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "UsEr",
  data() {
    let checkPassword=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value.length>15) {
        callback(new Error('密码范围1~15'));
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
    let checkRoleID = (rule, value, callback) => {
      if(value === '') {
        callback(new Error('请输入用户权限,用户权限:1为管理员,0为普通用户'));
      }
      else if(value !=1 && value !=0) {
        callback(new Error('请输入正确的用户权限'));
      }
      else{
        callback();
      }
    };
    let checkStatue = (rule, value, callback) => {
      if(value === '') {
        callback(new Error('请输入用户状态,用户状态:1为启用,0为禁用'));
      }
      else if(value !=1 && value !=0) {
        callback(new Error('请输入正确的用户状态'));
      }
      else{
        callback();
      }
    };
    return {
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      userName:'',
      usernameList:[],
      roleID:'',
      value:true,
      addDialog:false,
      updateDialog:false,
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      record:{
        roleID:'',
        userName:'',
        nickName:'',
        operation:'',
        location:'',
        id:'',
        name:'',
        time:'',
      },
      roleIDs:[{
          value: '0',
          label: '用户'
        }, {
          value: '1',
          label: '管理员'
        }],
      form:{
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
        userName: [
          {validator: checkUserName, required: true, trigger: "blur" }
        ],
        password: [
          {validator: checkPassword, required: true, trigger: "blur" }
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
        roleID: [
          {validator: checkRoleID, required: true, trigger: "blur" }
        ],
        statue: [
          {validator: checkStatue, required: true, trigger: "blur" }
        ],
      }
    }
  },
  methods:{
    changeStatue(row){
          this.$axios.post('http://localhost:8081/user/changeStatue',row).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改用户状态成功',
                type: 'success'
              });
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '修改用户状态失败',
                type: 'error'
              });
            }
          })
    },
    del(row){
      this.$axios.get("http://localhost:8081/user/delete?userID="+row.userID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除用户信息成功',
            type: 'success'
          });
          this.record.operation='删除用户'
          this.saveRecord(row)
          this.loadPost()
        }else{
          this.$message({
            showClose: true,
            message: '删除用户信息失败',
            type: 'error'
          });
        }
      })
    },
    update(row){
      this.updateDialog=true
      this.$nextTick(()=>{
        this.form.userID=row.userID
        this.form.userName=row.userName
        this.form.password=row.password
        this.form.name=row.name
        this.form.age=row.age
        this.form.sex=row.sex
        this.form.phone=row.phone
        this.form.email=row.email
        this.form.roleID=row.roleID
        this.form.statue=row.statue
      })
    },
    updateUser(){
      console.log(this.form)
      this.$refs.form.validate((valid) => {
        if (valid) {
              this.$axios.post('http://localhost:8081/user/update', this.form).then(res => res.data).then(res => {
                if (res.code === 888) {
                  this.$message({
                    showClose: true,
                    message: '修改用户信息成功',
                    type: 'success'
                  });
                  this.record.operation = '修改用户'
                  this.saveRecord(this.form)
                  this.updateDialog = false
                  this.roleID = ''
                  this.loadPost()
                } else {
                  this.$message({
                    showClose: true,
                    message: '修改用户信息失败',
                    type: 'error'
                  });
                }
              })
            }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写用户信息',
            type: 'error'
          });
        }
      })
    },
    addUser(){
      this.addDialog=true
      this.resetForm('form')
    },
    saveUser(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log(this.form)
          this.$axios.get("http://localhost:8081/user/select?userName="+this.form.userName).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '用户已经存在,请修改用户名',
                type: 'error'
              });
            } else {
              this.$axios.post('http://localhost:8081/user/save', this.form).then(res => res.data).then(res => {
                if (res.code === 888) {
                  this.$message({
                    showClose: true,
                    message: '新增用户信息成功',
                    type: 'success'
                  });
                  this.record.operation='新增用户'
                  this.saveRecord(this.form)
                  this.addDialog = false
                  this.roleID=''
                  this.loadPost()
                } else {
                  this.$message({
                    showClose: true,
                    message: '新增用户信息失败',
                    type: 'error'
                  });
                }
              })
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写用户信息',
          });
        }
      });
    },

    saveRecord(data){
      this.record.id=data.userID
      this.record.name=data.userName
      this.$axios.post("http://localhost:8081/record/save",this.record)
    },

    resetForm(form) {
      if (this.$refs[form] !== undefined) {
        this.$refs[form].resetFields();
      }
    },
    restParam(){
      this.username=''
      this.roleIDs=''
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    loadPost(){
      this.$axios.post('http://localhost:8081/user/selectPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          userName:this.userName,
          roleID:this.roleID,
        },
      }).then(res=>res.data).then(res=>{
        this.tableData=res.data
        this.total=res.total
      })
    }
  },
  beforeMount() {
    this.loadPost();
    this.record.userName=this.user.userName
    this.record.roleID=this.user.roleID
    this.record.nickName=this.user.name
    this.record.location='用户管理'
  }
}
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #6293e5;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>