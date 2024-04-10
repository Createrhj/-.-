<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input
          v-model="title"
          placeholder="请输入标题"
          style="width: 200px"
          suffix-icon="el-icon-search"></el-input>
      <el-select v-model="mark" clearable placeholder="请选择" style="margin-left:10px;width: 100px">
        <el-option
            v-for="item in marks"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
      <el-button type="primary" @click="add">提交意见<i class="el-icon-upload el-icon--right"></i></el-button>
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
            <el-form-item label="意见提交方">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="提交时间">
              <span>{{ props.row.time}}</span>
            </el-form-item>
            <el-form-item label="意见解决方">
              <span>{{ props.row.respondent }}</span>
            </el-form-item>
            <el-form-item label="意见标题">
              <span>{{ props.row.title }}</span>
            </el-form-item>
            <el-form-item label="意见详情">
              <span>{{ props.row.suggestion }}</span>
            </el-form-item>
            <el-form-item label="意见回复">
              <span>{{ props.row.reply }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          type="index"
          label="序号"
          width="80px">
      </el-table-column>

      <el-table-column prop="name" label="意见提交方" width="100">
      </el-table-column>

      <el-table-column prop="title" label="意见标题" width="100">
      </el-table-column>

      <el-table-column prop="time" label="提交时间" width="180">
      </el-table-column>

      <el-table-column prop="statue" label="是否解决" width="100">
        <template v-slot="scope">
          <div>
            <el-tag type="danger" style="margin: 5px" v-if="scope.row.mark===0">未解决</el-tag>
            <el-tag style="margin:5px" v-if="scope.row.mark===1">已解决</el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="300" v-if="this.user.roleID===1">
        <template v-slot="scope">
          <el-button type="success" @click="update(scope.row)" style="margin-right: 10px" >编辑</el-button>
          <el-button type="primary" @click="answer(scope.row)" style="margin-right: 10px" >回复</el-button>
          <el-popconfirm
              title="确定删除该意见信息吗？"
              @confirm="del(scope.row)"
          >
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--提交意见-->
    <el-dialog title="提交意见"
               :visible.sync="addDialog"
               width="30%"
               center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="意见标题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="意见反馈" prop="suggestion">
          <el-input type="textarea" v-model="form.suggestion"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">提交意见</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
    </el-dialog>

    <!--回复意见-->
    <el-dialog title="回复意见"
               :visible.sync="answerDialog"
               width="30%"
               center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="提交方" prop="name">
          <el-input v-model="form.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见标题" prop="title">
          <el-input v-model="form.title" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="提交时间" prop="title">
          <el-input v-model="form.time" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见反馈" prop="suggestion">
          <el-input type="textarea" v-model="form.suggestion" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见回复" prop="reply">
          <el-input type="textarea" v-model="form.reply"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="answerFeedback">提交意见</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>

    </el-dialog>

    <!--修改意见-->
    <el-dialog title="修改意见信息"
               :visible.sync="updateDialog"
               width="30%"
               center
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="提交方" prop="name">
          <el-input v-model="form.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见标题" prop="title">
          <el-input v-model="form.title" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="提交时间" prop="title">
          <el-input v-model="form.time" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见反馈" prop="suggestion">
          <el-input type="textarea" v-model="form.suggestion" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="意见回复" prop="reply">
          <el-input type="textarea" v-model="form.reply"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateFeedback">提交意见</el-button>
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
  name: "FeedBack",
  data() {
    let checkTitle=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入标题'));
      } else if (value.length>15) {
        callback(new Error('标题范围1~15'));
      } else {
        callback();
      }
    };
    let checkSuggestion=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入您的意见'));
      } else if (value.length>15) {
        callback(new Error('意见内容范围1~100'));
      } else {
        callback();
      }
    };
    let checkReply=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入您的解决方案'));
      } else if (value.length>15) {
        callback(new Error('解决方案范围1~100'));
      } else {
        callback();
      }
    };
    return {
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      title:'',
      mark:'',
      addDialog:false,
      updateDialog:false,
      answerDialog:false,
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      marks:[{
        value: '0',
        label: '未解决'
      }, {
        value: '1',
        label: '已解决'
      }],
      form:{
        name:'',
        title:'',
        suggestion:'',
        reply:'',
        respondent:'',
        time:'',
        mark:''
      },
      rules: {
        title: [
          {validator: checkTitle, required: true, trigger: "blur" }
        ],
        suggestion: [
          {validator: checkSuggestion, required: true, trigger: "blur" }
        ],
        reply: [
          {validator: checkReply, required: true, trigger: "blur" }
        ],
      }
    }
  },
  methods:{

    del(row){
      this.$axios.post("http://localhost:8081/feedback/delFeedback",row).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除意见信息成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            showClose: true,
            message: '删除意见信息失败',
            type: 'error'
          });
        }
      })
    },

    update(row){
      this.updateDialog=true
      this.$nextTick(()=>{
        this.form.name=row.name
        this.form.title=row.title
        this.form.time=row.time
        this.form.suggestion=row.suggestion
        this.form.reply=row.reply
        this.form.respondent=this.user.name
        this.form.mark=row.mark
      })
    },
    updateFeedback(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8081/feedback/updateFeedback', this.form).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改意见信息成功',
                type: 'success'
              });
              this.updateDialog=false
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '修改意见信息失败',
                type: 'error'
              });
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写意见信息',
            type: 'error'
          });
        }
      })
    },

    answer(row){
      this.answerDialog=true
      this.$nextTick(()=>{
        this.form.name=row.name
        this.form.title=row.title
        this.form.time=row.time
        this.form.suggestion=row.suggestion
      })
    },
    answerFeedback(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.form.respondent=this.user.name
          this.form.mark=1
          this.$axios.post('http://localhost:8081/feedback/updateFeedback', this.form).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '回复意见成功',
                type: 'success'
              });
              this.answerDialog=false
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '回复意见失败',
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

    add(){
      this.addDialog=true
      this.resetForm('form')
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.form.name=this.user.name
          this.form.mark=0
          this.$axios.post('http://localhost:8081/feedback/addFeedback', this.form).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '提交意见成功',
                type: 'success'
              });
              this.addDialog = false
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '提交意见失败',
                type: 'error'
              });
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

    resetForm(form) {
      if (this.$refs[form] !== undefined) {
        this.$refs[form].resetFields();
      }
    },
    restParam(){
      this.title=''
      this.mark=''
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
      this.$axios.post('http://localhost:8081/feedback/selectPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          mark:this.mark,
          title:this.title
        },
      }).then(res=>res.data).then(res=>{
        this.tableData=res.data
        this.total=res.total
      })
    }
  },
  beforeMount() {
    this.loadPost();
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