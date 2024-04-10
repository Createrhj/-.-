<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input v-model="teaName" placeholder="请输入花茶名称"
                style="width: 200px"
                @keyup.enter.native="loadPost"
                suffix-icon="el-icon-search"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
      <el-button type="primary" @click="add" v-if="this.user.roleID!==0">新增<i class="el-icon-upload el-icon--right"></i></el-button>
    </div>
    <!--花茶信息-->
    <el-table :data="tableData"
              :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
              border
              style="overflow: auto;height: 506px;margin-bottom: 10px"
    >
      <el-table-column type="expand">
        <template v-slot="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="花茶编号">
              <span>{{ props.row.teaID }}</span>
            </el-form-item>
            <el-form-item label="花茶名称">
              <span>{{ props.row.teaName }}</span>
            </el-form-item>
            <el-form-item label="煮茶温度">
              <span>{{ props.row.temperature }}</span>
            </el-form-item>
            <el-form-item label="煮茶时间">
              <span>{{ props.row.teaTime }}</span>
            </el-form-item>
            <el-form-item label="花茶功效">
              <span>{{ props.row.teaFunction }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          type="index"
          label="序号"
          width="80">
      </el-table-column>
      <el-table-column prop="teaName" label="花茶名称" width="180">
      </el-table-column>
      <el-table-column prop="isMaterial" label="花茶所需材料配比" width="175">
        <template v-slot="scope">
        <el-button type="primary" icon="el-icon-view" @click="myMaterial(scope.row.teaID)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column prop="collection" label="收藏" width="150">
        <template v-slot="scope">
          <el-button type="warning" icon="el-icon-star-off" @click="addCollection(scope.row)"></el-button>
        </template>
      </el-table-column>

      <el-table-column prop="operate" label="操作" width="225" v-if="this.user.roleID!==0">
        <template v-slot="scope">
          <el-button type="success" @click="update(scope.row)" style="margin-right: 10px">编辑</el-button>
          <el-popconfirm
              title="确定删除该花茶信息吗？"
              @confirm="del(scope.row)"
          >
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>
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
    <!--添加花茶信息-->
    <el-dialog title="添加花茶信息"
               :visible.sync="addDialogVisible"
               width="30%"
               center>
      <el-form ref="teaForm" :model="teaForm" :rules="rules" label-width="80px">
        <el-form-item label="花茶名称" prop="teaName">
          <el-input v-model="teaForm.teaName"></el-input>
        </el-form-item>
        <el-form-item label="所需时间" prop="teaTime">
          <el-input v-model="teaForm.teaTime"></el-input>
        </el-form-item>
        <el-form-item label="所需温度" prop="temperature">
          <el-input v-model="teaForm.temperature"></el-input>
        </el-form-item>
        <el-form-item label="花茶功效" prop="teaFunction">
          <el-input v-model="teaForm.teaFunction"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveTea">确定</el-button>
        <el-button @click="resetForm('teaForm')">重置</el-button>
     </span>
    </el-dialog>

    <el-dialog  title="修改花茶信息"
                :visible.sync="updateDialogVisible"
                width="30%"
                center>
      <el-form ref="teaForm" :model="teaForm" :rules="rules" label-width="80px">
        <el-form-item label="花茶名称" prop="teaName">
          <el-input v-model="teaForm.teaName"></el-input>
        </el-form-item>
        <el-form-item label="所需时间" prop="teaTime">
          <el-input v-model="teaForm.teaTime"></el-input>
        </el-form-item>
        <el-form-item label="所需温度" prop="temperature">
          <el-input v-model="teaForm.temperature"></el-input>
        </el-form-item>
        <el-form-item label="花茶功效" prop="teaFunction">
          <el-input v-model="teaForm.teaFunction"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateTea">修改</el-button>
        <el-button @click="resetForm('teaForm')">重置</el-button>
     </span>
    </el-dialog>

    <el-dialog title="花茶所需材料" :visible.sync="materialDialog">
      <el-button type="primary" @click="addMaterial" v-if="user.roleID!==0">新增<i class="el-icon-upload el-icon--right"></i></el-button>
      <el-table :data="materialData" style="margin-left: 10px">
        <el-table-column type="expand">
          <template v-slot="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="材料编号">
                <span>{{ props.row.materialID }}</span>
              </el-form-item>
              <el-form-item label="材料昵称">
                <span>{{ props.row.nickName }}</span>
              </el-form-item>
              <el-form-item label="主要产地">
                <span>{{ props.row.production }}</span>
              </el-form-item>
              <el-form-item label="主要成分">
                <span>{{ props.row.composition }}</span>
              </el-form-item>
              <el-form-item label="性味">
                <span>{{ props.row.flavor }}</span>
              </el-form-item>
              <el-form-item label="价格">
                <span>{{ props.row.price }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
            type="index"
            label="序号"
            width="80">
        </el-table-column>
        <el-table-column property="materialName" label="材料名称" width="100"></el-table-column>
        <el-table-column property="classification" label="材料类别" width="100"></el-table-column>
        <el-table-column property="weight" label="所需重量" width="100" ></el-table-column>
        <el-table-column property="operate" label="操作" width="180" v-if="user.roleID!==0">
          <template v-slot="scope">
            <el-button type="success" @click="updateMaterial(scope.row)" style="margin-right: 10px" >编辑</el-button>
            <el-popconfirm
                title="确定删除该材料信息吗？"
                @confirm="delMaterial(scope.row)"
            >
              <el-button slot="reference" type="danger">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog  title="新增花茶材料信息"
                :visible.sync="addMaterialDialogVisible"
                width="30%"
                center>
      <el-form ref="materialForm" :model="materialForm" :rules="rule" label-width="80px">
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="materialForm.materialName" @change="selectMaterial"></el-input>
        </el-form-item>
        <el-form-item label="材料类别" prop="classification">
          <el-input v-model="materialForm.classification"></el-input>
        </el-form-item>
        <el-form-item label="所需克数" prop="weight">
          <el-input v-model="materialForm.weight"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveMaterial">添加</el-button>
        <el-button @click="resetForm('materialForm')">重置</el-button>
     </span>
    </el-dialog>

    <el-dialog  title="修改花茶配比信息"
                :visible.sync="updateMaterialDialogVisible"
                width="30%"
                center>
      <el-form ref="materialForm" :model="materialForm" :rules="rule" label-width="80px">
        <el-form-item label="所需克数" prop="weight">
          <el-input v-model="materialForm.weight"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modMaterial">修改</el-button>
        <el-button @click="resetForm('materialForm')">重置</el-button>
     </span>
    </el-dialog>

  </div>

</template>

<script>
export default {
  name: "TeA",
  data() {
    let checkTeaName=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入花茶名称'));
      } else if (value.length>10) {
        callback(new Error('花茶名称范围1~10'));
      } else {
        callback();
      }
    };
    let checkTemperature=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入泡茶所需温度'));
      } else if (value>100) {
        callback(new Error('所需温度范围小于100摄氏度'));
      } else {
        callback();
      }
    };
    let checkTeaTime=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入泡茶所需时间'));
      } else if (value >1440) {
        callback(new Error('泡茶所需时间过长'));
      } else {
        callback();
      }
    };
    let checkTeaFunction=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入花茶功效'));
      } else if (value.length>50) {
        callback(new Error('花茶功效描述范围1~50'));
      } else {
        callback();
      }
    };
    let checkWeight=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入所需材料克数'));
      } else if (value >100) {
        callback(new Error('所需材料克数过重'));
      } else {
        callback();
      }
    };
    return {
      pageSize:10,
      pageNum:1,
      total:0,
      teaName:'',
      teaID:'',
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

      tableData:[],
      materialData:[],
      addDialogVisible:false,
      updateDialogVisible:false,
      materialDialog:false,
      addMaterialDialogVisible:false,
      updateMaterialDialogVisible:false,

      materialForm:{
        materialID:'',
        materialName:'',
        classification:'',
        weight:'',
        teaID:''
      },
      teaForm:{
        teaID:'',
        teaName:'',
        teaTime:'',
        temperature:'',
        teaFunction:''
      },
      rules: {
        teaName: [
          {validator: checkTeaName, required: true, trigger: "blur" }
        ],
        teaTime: [
          {validator: checkTeaTime, required: true, trigger: "blur" }
        ],
        temperature: [
          {validator: checkTemperature, required: true, trigger: "blur" }
        ],
        teaFunction: [
          {validator: checkTeaFunction, required: true, trigger: "blur" }
        ],
      },
      rule:{
        materialID: [
          { required: true, message: '请输入材料编号', trigger: 'blur' },
        ],
        materialName: [
          { required: true, message: '请选输入材料名称', trigger: 'blur' }
        ],
        classification: [
          { required: true, message: '请选输入材料类别', trigger: 'blur' }
        ],
        weight: [
          {validator: checkWeight, required: true, trigger: "blur" }
        ],
      },

    }
  },
  methods:{
    addCollection(collection) {
      collection.userName=this.user.userName
      collection.userID=this.user.userID
      collection.name=this.user.name
      this.$axios.get('http://localhost:8081/collection/haveCollection?teaID='+collection.teaID+"&userID="+collection.userID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '已经存在该花茶,添加收藏失败',
            type: 'warning'
          });
        }else{
          this.$axios.post('http://localhost:8081/collection/save',collection).then(res=>res.data).then(res=> {
            if(res.code===888) {
              this.$message({
                showClose: true,
                message: '添加收藏成功',
                type: 'success'
              });
              this.record.id = collection.teaID
              this.record.name = collection.teaname
              this.record.operation = '新增收藏'
              this.$axios.post("http://localhost:8081/record/save", this.record)
            }else {
              this.$message({
                showClose: true,
                message: '添加收藏失败',
                type: 'error'
              });
            }
          })
        }
      })
    },

    myMaterial(teaID){
        this.teaID=teaID
        this.materialDialog=true
        this.$axios.get('http://localhost:8081/tea/selectMaterial?teaID='+teaID).then(res=>res.data).then(res=>{
        this.materialData=res.data
          console.log(this.materialData)
      })
    },

    delMaterial(row){
      this.$axios.get("http://localhost:8081/tea/delMaterial?teaID="+row.teaID+"&materialID="+row.materialID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除配比信息成功',
            type: 'success'
          });
          this.myMaterial(row.teaID)
        }else{
          this.$message({
            showClose: true,
            message: '删除配比信息失败',
            type: 'error'
          });
        }
      })
    },
    addMaterial(){
      this.addMaterialDialogVisible=true
      this.materialForm.materialid=''
      this.resetForm('materialForm')
    },
    saveMaterial(){
      this.$refs.materialForm.validate((valid) => {
        if (valid) {
          this.materialForm.teaID=this.teaID
          console.log(this.materialForm)
          this.$axios.get('http://localhost:8081/tea/haveMaterial?teaID='+this.materialForm.teaID+'&materialID='+this.materialForm.materialID).then(res=>res.data).then(res=>{
            if(res.code===888){
              this.$message({
                showClose: true,
                message: '已经存在该材料',
                type: 'warning'
              });
            }else{
              this.$axios.post('http://localhost:8081/tea/addMaterial',this.materialForm).then(res=>res.data).then(res=>{
                if(res.code===888){
                  this.$message({
                    showClose: true,
                    message: '新增配比信息成功',
                    type: 'success'
                  });
                  this.addMaterialDialogVisible=false
                  this.myMaterial(this.teaID)
                }else{
                  this.$message({
                    showClose: true,
                    message: '请完整填写配比信息',
                    type: 'warning'
                  });
                }
              })
            }
          })

        } else {
          this.$message({
            showClose: true,
            message: '请完整填写材料信息',
          });
        }
      });
    },
    selectMaterial(){
      this.$axios.post('http://localhost:8081/material/selectByName',this.materialForm).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.materialForm.materialID=res.data.materialID
          this.materialForm.materialName=res.data.materialName
          this.materialForm.classification=res.data.classification
        }else{
          this.$message({
            showClose: true,
            message: '不存在该花茶材料',
            type: 'error'
          });
        }
      })
    },
    updateMaterial(row){
      this.updateMaterialDialogVisible=true
      this.$nextTick(()=>{
        this.materialForm.teaID=row.teaID
        this.materialForm.materialID=row.materialID
        this.materialForm.weight = row.weight
      })
    },
    modMaterial(){
      this.$refs.materialForm.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8081/tea/updateMaterial', this.materialForm).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改花茶配比信息成功',
                type: 'success'
              });
              this.updateMaterialDialogVisible=false
              this.myMaterial(this.teaID)
            } else {
              this.$message({
                showClose: true,
                message: '修改花茶配比信息失败',
                type: 'error'
              });
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写配比信息',
            type:'warning'
          });
        }
      });
    },

    del(row){
      this.$axios.get("http://localhost:8081/tea/delete?teaID="+row.teaID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除花茶信息成功',
            type: 'success'
          });
          this.record.id=row.teaID
          this.record.name=row.teaName
          this.record.operation='删除花茶'
          this.$axios.post("http://localhost:8081/record/save",this.record)
          this.loadPost()
        }else if (res.code===404){
          this.$router.replace("/Error")
        }
        else{
          this.$message({
            showClose: true,
            message: '删除花茶信息失败',
            type: 'error'
          });
        }
      })
    },
    update(row){
      this.updateDialogVisible=true
      this.$nextTick(()=>{
        this.teaForm.teaID = row.teaID
        this.teaForm.teaName = row.teaName
        this.teaForm.teaTime = row.teaTime
        this.teaForm.temperature = row.temperature
        this.teaForm.teaFunction = row.teaFunction
      })
    },
    updateTea(){
      this.$refs.teaForm.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8081/tea/update', this.teaForm).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改花茶信息成功',
                type: 'success'
              });
              this.record.id=this.teaForm.teaID
              this.record.name=this.teaForm.teaName
              this.record.operation='修改花茶'
              this.$axios.post("http://localhost:8081/record/save",this.record)

              this.updateDialogVisible=false
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '修改花茶信息失败',
                type: 'error'
              });
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写花茶信息',
            type:'warning'
          });
        }
      });
    },
    add(){
      this.addDialogVisible=true
      this.resetForm('teaForm')
    },
    saveTea(){
      this.$refs.teaForm.validate((valid) => {
        if (valid) {
          this.$axios.get('http://localhost:8081/tea/select?teaName='+this.teaForm.teaName).then(res=>res.data).then(res=>{
            if(res.code===888){
              this.$message({
                showClose: true,
                message: '已经存在该花茶',
                type: 'warning'
              });
            }else {
              this.$axios.post('http://localhost:8081/tea/save',this.teaForm).then(res=>res.data).then(res=>{
                if(res.code===888){
                  this.$message({
                    showClose: true,
                    message: '新增花茶信息成功',
                    type: 'success'
                  });
                  this.record.id=this.teaForm.teaID
                  this.record.name=this.teaForm.teaName
                  this.record.operation='新增花茶'
                  this.$axios.post("http://localhost:8081/record/save",this.record)
                  this.addDialogVisible=false
                  this.loadPost()
                }else{
                  this.$message({
                    showClose: true,
                    message: '新增花茶信息失败',
                    type: 'error'
                  });
                }
              })
            }
          })
        } else {
          this.$message({
            showClose: true,
            message: '请完整填写花茶信息',
            type:'warning'
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
      this.teaName=''
    },

    handleSizeChange(val) {
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      this.pageNum=val
      this.loadPost()
    },
    loadPost(){
      this.$axios.post('http://localhost:8081/tea/selectPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          teaName:this.teaName
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
    this.record.location='花茶管理'
  }
}
</script>

<style scoped>

.demo-table-expand label {
  width: 150px;
  color: #6293e5;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>