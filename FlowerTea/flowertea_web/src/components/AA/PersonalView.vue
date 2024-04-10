<template>
    <div class="viewOfPersonalData">
<!--      头像和消息通知区域-->
        <div class="informationArea">
<!--            头像区-->
            <div class="headAreaOfUser">
              <div style="height:50px;width: 50px;border-radius: 10px;overflow: hidden">
                  <img src="../../assets/images/touxiang.png" style="width: 100%;height: 100%">
              </div>
                <el-dropdown>
                <span style="margin-left: 10px;font-size: 14px;color: black;padding-left: 10px" class="el-dropdown-link">
                {{this.user.name}}<i class="el-icon-arrow-down el-icon--right"></i>
                 </span>
                    <el-dropdown-menu slot="dropdown" class="liList">
                      <el-dropdown-item >
                        <span style="width: 100%;height: 100%" @click="closeTimeArea">日期与时间</span>
                      </el-dropdown-item>
                        <el-dropdown-item >
                            <span style="width: 100%;height: 100%" @click="closePersonalDataArea">个人信息</span>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <span style="width: 100%;height: 100%" @click="closeNoticeArea">相关数据</span>
                        </el-dropdown-item>
                      <el-dropdown-item>
                        <span style="width: 100%;height: 100%" @click="toLogin">退出登录</span>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
<!--            图标区-->
            <div class="IconArea">
              <el-badge :value="this.feedbackTotal" style="margin-right: 15px" v-if="this.user.roleID===1">
              <i style="display: block;
                width: 30px;
                height: 30px;
                border-radius: 15px;
                background-color:#FFFFFF;
                text-align: center;
                line-height: 30px" class="el-icon-message-solid" @click="unresolved"></i>
              </el-badge>
              <el-badge :value="this.feedbackTotal" style="margin-right: 15px" v-if="this.user.roleID===0">
                <i style="display: block;
                width: 30px;
                height: 30px;
                border-radius: 15px;
                background-color:#FFFFFF;
                text-align: center;
                line-height: 30px" class="el-icon-message-solid" @click="myFeedback"></i>
              </el-badge>
              <i style="display: block;
                width: 30px;
                height: 30px;
                border-radius: 15px;
                background-color:#FFFFFF;
                text-align: center;
                line-height: 30px" class="el-icon-remove-outline" @click="displayNone"></i>
            </div>
        </div>

        <div class="mainBodyOfPersonalCenter">
            <transition-group name="list-complete" tag="p" appear>
              <!--日期与倒计时-->
                <div v-show="flagOfTime" class="timeArea" :key="1">
                    <i style="position: absolute; right: 15px;top: 10px;font-size: 18px;font-weight: bold;z-index:5" class="el-icon-close close" @click="closeTimeArea"></i>
                  <span style="margin-top: 20px">距离明日:</span>
                  <el-statistic
                      ref="statistic"
                      format="HH:mm:ss"
                      :value="deadline"
                      time-indices
                      style="margin-top: 0px"
                  >
                  </el-statistic>
                  <span>{{this.today}}</span>
                </div>
              <!--个人信息区域-->
            <div v-show="flagOfPersonalData" class="personalDataArea" :key="2">
                <i style="position: absolute; right: 15px;top: 10px;font-size: 18px;font-weight: bolder;z-index:5 " class="el-icon-close close" @click="closePersonalDataArea"></i>
                <div class="headOfPersonData">
                    <span style="flex: 3;margin-left: 10px">我的名片</span>
                </div>
                <div class="mainBodyOfPersonalData">
                    <div v-show="flagOfUserData" class="defaultInformation" >
                        <div>
                        用&nbsp;户&nbsp;&nbsp;名:
                            <span>{{user.userName}}</span>
                        </div>
                        <div>
                        昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:
                            <span>{{user.name}}</span>
                        </div>
                        <div>
                          年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄:
                          <span>{{user.age}}</span>
                        </div>
                        <div>
                        性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:
                        <span>{{user.sex}}</span>
                        </div>
                        <div>
                        身&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:
                            <span v-if="this.user.roleID==1">管理员</span>
                            <span v-if="this.user.roleID==0">用户</span>
                        </div>
                    </div>
                </div>
            </div>
              <!--消息通知-->
            <div v-show="flagOfNotice" class="noticeArea" :key="4">
                <i style="position: absolute; right: 15px;top: 10px;font-size: 18px;font-weight: bold;z-index:5" class="el-icon-close close" @click="closeNoticeArea"></i>
              <span style="flex: 1;display: flex;align-items: center;color: #333;padding-left: 15px;font-weight: bolder;font-size: 16px">相关数据</span>
              <el-carousel height="150px">
                <el-carousel-item v-for="item in carouseData" :key="item.id">
                  <h4 align="center">{{item.title}}</h4>
                  <div style="text-align: center">
                  <i class="el-icon-menu">
                    <span style="font-weight: bold;font-size: 20px;font-family: 楷体;margin-left: 5px" >当前数量为:{{item.total}}</span>
                  </i>
                  </div>
                </el-carousel-item>
              </el-carousel>
            </div>
            </transition-group>
        </div>

      <el-dialog title="未解决的意见反馈" :visible.sync="unResolveDialog" :append-to-body="true">
        <el-table :data="tableData"
                  :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
                  border
                  style="overflow: auto;height: 382px; margin-bottom: 10px"
        >
          <el-table-column
              type="index"
              label="序号"
              width="50px">
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
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="operate" label="操作" width="180">
            <template v-slot="scope">
              <el-button type="primary" @click="answer(scope.row)" style="margin-right: 10px">回复</el-button>
              <el-popconfirm
                  title="确定删除该意见信息吗？"
                  @confirm="del(scope.row)"
              >
                <el-button slot="reference" type="danger">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <!--回复意见-->
      <el-dialog title="回复意见"
                 :visible.sync="answerDialog"
                 width="30%"
                 center
                 :append-to-body="true">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="提交方" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="意见标题" prop="title">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="提交时间" prop="title">
            <el-input v-model="form.time"></el-input>
          </el-form-item>
          <el-form-item label="意见反馈" prop="suggestion">
            <el-input type="textarea" v-model="form.suggestion"></el-input>
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

      <el-dialog title="我的意见反馈" :visible.sync="myFeedbackDialog" :append-to-body="true">
        <el-table :data="tableData"
                  :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
                  border
                  style="overflow: auto;height: 382px;margin-bottom: 10px"
        >
          <el-table-column
              type="index"
              label="序号"
              width="50px">
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
                <el-tag type="primary" style="margin: 5px" v-if="scope.row.mark===1">已解决</el-tag>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="operate" label="操作" width="180">
            <template v-slot="scope">
              <el-button type="primary" @click="updateFeedback(scope.row)" style="margin-right: 10px">修改</el-button>
              <el-popconfirm
                  title="确定删除该意见信息吗？"
                  @confirm="del(scope.row)"
              >
                <el-button slot="reference" type="danger">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <!--修改意见-->
      <el-dialog title="修改意见信息"
                 :visible.sync="updateDialog"
                 width="30%"
                 center
                 :append-to-body="true"
      >
        <el-form ref="feedbackForm" :model="feedbackForm" :rules="rules" label-width="80px">
          <el-form-item label="意见标题" prop="title">
            <el-input v-model="feedbackForm.title"></el-input>
          </el-form-item>
          <el-form-item label="意见反馈" prop="suggestion">
            <el-input type="textarea" v-model="feedbackForm.suggestion"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="update">提交意见</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
      </el-dialog>



    </div>
</template>

<script>
import 'animate.css'
export default {
    name: "PersonalView",
    data(){
      let checkReply=(rule,value,callback)=>{
        if (value === '') {
          callback(new Error('请输入您的解决方案'));
        } else if (value.length>15) {
          callback(new Error('解决方案范围1~100'));
        } else {
          callback();
        }
      };
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
      return{
          deadline: Date.now() + (new Date().setHours(23, 59, 59) - Date.now()),
          today:new Date().toLocaleDateString(),
          numberOfRequest:0,
          flagOfPersonalCenter:false,
          flagOfTime:true,
          flagOfPersonalData:true,
          flagOfNotice:true,
          user : JSON.parse(sessionStorage.getItem('CurUser')),
          flagOfUserData:true,
          dialogVisible: false,
          feedbackTotal:'',
          feedback:'',
          mark:'',
          name:'',
          answerDialog:false,
          unResolveDialog:false,
          myFeedbackDialog:false,
          updateDialog:false,
          tableData:[],
          feedbackForm:{
            name:'',
            title:'',
            suggestion:'',
            time:'',
            mark:''
          },
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
          },
          carouseData: [
            { id:0,title:"当前花茶总数",total:''},
            { id:1,title:"当前花茶材料总数",total: ''},
            { id:2,title:"当前用户总数",total: ''},
            ],
        }
    },
    methods:{
      myFeedback(){
        this.myFeedbackDialog=true;
        this.name=this.user.name
        this.loadPost()
      },
      updateFeedback(row){
        this.updateDialog=true
        this.$nextTick(()=>{
          this.feedbackForm.name=row.name
          this.feedbackForm.time=row.time
          this.feedbackForm.mark=row.mark
          this.feedbackForm.title=row.title
          this.feedbackForm.suggestion=row.suggestion
        })
      },
      update(){
        this.$refs.feedbackForm.validate((valid) => {
          if (valid) {
            if (this.feedbackForm.mark===1){
              this.$message({
                showClose: true,
                message: '该意见信息不可修改',
                type: 'warning'
              });
            }else {
              this.$axios.post('http://localhost:8081/feedback/updateFeedback', this.feedbackForm).then(res => res.data).then(res => {
                if (res.code === 888) {
                  this.$message({
                    showClose: true,
                    message: '修改意见信息成功',
                    type: 'success'
                  });
                  this.updateDialog = false
                } else {
                  this.$message({
                    showClose: true,
                    message: '修改意见信息失败',
                    type: 'error'
                  });
                }
              })
            }
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

      unresolved(){
          this.unResolveDialog=true;
          this.mark='0'
          this.loadPost()
        },
      answer(row){
          this.answerDialog=true
          this.$nextTick(()=>{
            this.form.name=row.name
            this.form.title=row.title
            this.form.time=row.time
            this.form.respondent=this.user.name
            this.form.suggestion=row.suggestion
          })
        },
      answerFeedback(){
        this.$refs.form.validate((valid) => {
          if (valid) {
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
      del(row){
        this.$axios.post("http://localhost:8081/feedback/delFeedback",row).then(res=>res.data).then(res=>{
          if(res.code===888){
            this.$message({
              showClose: true,
              message: '删除意见信息成功',
              type: 'success'
            });
            this.record.operation='删除意见'
            this.saveRecord(row)
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
      resetForm(form) {
        if (this.$refs[form] !== undefined) {
          this.$refs[form].resetFields();
        }
      },
      loadPost(){
        this.$axios.post('http://localhost:8081/feedback/selectPage',{
          pageSize:this.pageSize,
          pageNum:this.pageNum,
          param:{
            mark:this.mark,
            name:this.name
          },
        }).then(res=>res.data).then(res=>{
          this.tableData=res.data
          this.total=res.total
        })
      },


      getTeaTotal(){
          this.$axios.post("http://localhost:8081/tea/getTotal").then(res=>res.data).then(res=>{
            this.carouseData[0].total=res
          })
        },
      getMaterialTotal(){
          this.$axios.post("http://localhost:8081/material/getTotal").then(res=>res.data).then(res=>{
            this.materialTotal=res
            this.carouseData[1].total=res
          })
        },
      getUserTotal(){
          this.$axios.post("http://localhost:8081/user/getTotal").then(res=>res.data).then(res=>{
            this.carouseData[2].total=res
          })
        },
      getFeedbackTotal(){
        this.$axios.get("http://localhost:8081/feedback/getTotal?mark="+0).then(res=>res.data).then(res=>{
          if(res!=0) {
            this.feedbackTotal = res
          }
        })
      },
      getFeedback(){
        this.$axios.get("http://localhost:8081/feedback/getTotal?mark="+0+"&name="+this.user.name).then(res=>res.data).then(res=>{
          if(res!=0) {
            this.feedback = res
          }
        })
      },

      toLogin(){
          this.$router.replace('/');
          sessionStorage.removeItem('CurUser')
        },
      displayNone(){
            this.$bus.$emit('personalCenterChange',this.flagOfPersonalCenter)
        },
      closeTimeArea(){
            this.flagOfTime = !this.flagOfTime
        },
      closePersonalDataArea(){
            this.flagOfPersonalData = !this.flagOfPersonalData
        },
      closeNoticeArea(){
            this.flagOfnotice = !this.flagOfnotice
        },
    },
  beforeMount() {
    this.getTeaTotal()
    this.getMaterialTotal()
    this.getUserTotal()
    this.getFeedbackTotal()
    this.getFeedback()
  }
}
</script>

<style scoped>
.viewOfPersonalData{
    width: 300px;
    height: 650px;
    background-color: #F5F7F9;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    right: 0px;
}
    .informationArea{
        width: 80%;
        flex: 2;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-left: 30px;
    }
    .mainBodyOfPersonalCenter{
        flex: 14;
        width: 100%;
        overflow-y: auto;
        position: relative;
    }
    .mainBodyOfPersonalCenter div{
        border-radius: 15px;
        margin-top: 20px;
    }
    .timeArea{
      width: 80%;
      height: 100px;
      background-color: white;
      margin-left: 30px;
      position: relative;
      display: flex;
      align-items: center;
      flex-direction: column;
      background-image: radial-gradient(circle farthest-side at 10% 90%, #FFE8EA, #EDF3FF 70%, #EDF2FB);
    }
    .personalDataArea{
        width: 80%;
        height: 170px;
        background-color: white;
        margin-left: 30px;
        position: relative;
        display: flex;
        align-items: center;
        flex-direction: column;
        background-image: radial-gradient(circle farthest-side at 10% 90%, #FFE8EA, #EDF3FF 70%, #EDF2FB);
    }

    .personalDataArea div{
    margin-top: 0px;
    }

    .noticeArea{
        width: 80%;
        height: 200px;
        margin-left: 30px;
        position: relative;
        display: flex;
        flex-direction: column;
        justify-content: center;
        overflow-y: auto;
        background-image: radial-gradient(circle farthest-side at 10% 90%, #FCEAED, #EDF3FF 70%, #EDF2FB);
    }
    .noticeArea div{
        margin-top: 0px;
    }
    .headAreaOfUser{
        flex: 5;
        height: 100%;
        display: flex;
        align-items: center;
    }
.tineArea div{
    margin-top: 0px;
}
    .IconArea{
        flex: 2;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 5px;
        font-size: 16px;
    }
    .IconArea i:hover{
        color: #257B5E;
        cursor: pointer;
    }
    .liList li{
    text-align: center;
    }
    .liList li:hover{
        color: #257B5E;
        background-color: rgba(37,123,94,0.1);
    }
.mainBodyOfPersonalCenter div {
    transition: all 0.5s;
}

    .mainBodyOfPersonalCenter div:hover{
        transform: scale(1.01,1.01);
    }
    .close:hover{
        cursor: pointer;
        color: #2b542c;
    }
    .headOfPersonData{
        height: 100%;
        width: 100%;
        flex: 2;
        display: flex;
        align-items: center;
        padding: 0 15px;
        font-weight: bold;
        font-size: 16px;
    }
    .mainBodyOfPersonalData{
        flex: 6;
        width: 100%;
    }
    .defaultInformation{
        display: flex;
        flex-direction: column;
        align-items: center;
        height: 100%;
        width: 100%;
        margin-left: 10px;
    }

.defaultInformation:hover{
    cursor: pointer;
    background-color: rgba(231,234,237,0.7);
    transform: scale(0,0);
    width: 99%;
    border-radius: 10px;
}
.defaultInformation div{
    flex: 1;
    height: 100%;
    font-size: 13px;
    font-weight: bold;
    color: #464646;
    width: 100%;
    padding: 0 15px;
}
</style>