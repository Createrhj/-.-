<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input v-model="teaName"
                placeholder="请输入花茶名称"
                style="width: 200px;margin-right: 10px"
                @keyup.enter.native="loadPost"
                suffix-icon="el-icon-search"></el-input>
      <el-input v-model="userName"
                placeholder="请输入用户名称"
                style="width: 200px"
                @keyup.enter.native="loadPost"
                suffix-icon="el-icon-search"
                v-show="this.user.roleID===1"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
    </div>

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
          <el-form-item label="用户编号">
            <span>{{ props.row.userID }}</span>
          </el-form-item>
          <el-form-item label="用户昵称">
            <span>{{ props.row.name }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
        type="index"
        label="序号"
        width="80">
    </el-table-column>
    <el-table-column prop="time" label="收藏时间" width="200">
    </el-table-column>
    <el-table-column prop="userName" label="用户名" width="140" v-if="this.user.roleID===1">
    </el-table-column>
    <el-table-column prop="teaName" label="花茶名称" width="150">
    </el-table-column>
    <el-table-column prop="isMaterial" label="花茶所需材料配比" width="150">
      <template v-slot="scope">
        <el-button type="primary" icon="el-icon-view" @click="myMaterial(scope.row.teaID)">查看</el-button>
      </template>
    </el-table-column>
    <el-table-column prop="operate" label="操作" width="150">
      <template v-slot="scope">
        <el-popconfirm
            title="确定删除该收藏信息吗？"
            @confirm="del(scope.row)"
        >
          <el-button slot="reference" type="danger">删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>

  </el-table>

  <el-dialog title="花茶所需材料" :visible.sync="materialDialog">
    <el-table :data="materialData">
      <el-table-column property="materialID" label="材料编号" width="100"></el-table-column>
      <el-table-column property="materialName" label="材料名称" width="100"></el-table-column>
      <el-table-column property="classification" label="材料类别" width="100"></el-table-column>
      <el-table-column property="weight" label="材料所需重量" width="150" ></el-table-column>
    </el-table>
  </el-dialog>

    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "MyCollection",
  data(){
    return{
      pageSize:10,
      pageNum:1,
      total:0,
      tableData:[],
      materialData:[],
      teaID:'',
      teaName:'',
      userName:'',
      materialDialog:false,
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
    }
  },

  methods:{
    myMaterial(teaID){
      this.teaID=teaID
      this.materialDialog=true
      this.$axios.get('http://localhost:8081/tea/selectMaterial?teaID='+teaID).then(res=>res.data).then(res=>{
        this.materialData=res.data
      })
    },
    del(row){
      this.$axios.get("http://localhost:8081/collection/delete?teaID="+row.teaID+"&userID="+row.userID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除收藏成功',
            type: 'success'
          });
          this.record.ID=row.teaID
          this.record.name=row.teaName
          this.record.operation='删除收藏'
          this.$axios.post("http://localhost:8081/record/save",this.record)
          this.loadPost()
        }else{
          this.$message({
            showClose: true,
            message: '删除收藏失败',
            type: 'error'
          });
        }
      })
    },

    handleSizeChange(val) {
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    loadPost(){
      if(this.user.roleID===0) {
        this.$axios.post('http://localhost:8081/collection/selectPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            teaName: this.teaName,
            userName: this.user.userName
          }
        }).then(res => res.data).then(res => {
          console.log(this.tableData)
          this.tableData = res.data
          this.total = res.total
        })
      }else {
        this.$axios.post('http://localhost:8081/collection/selectPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            teaName: this.teaName,
            userName: this.userName
          }
        }).then(res => res.data).then(res => {
          console.log(res.data)
          this.tableData = res.data
          this.total = res.total
        })
      }
    },
    restParam(){
      this.teaname=''
      this.username=''
    }
  },
  beforeMount() {
    this.loadPost();
    this.record.userName=this.user.userName
    this.record.roleID=this.user.roleID
    this.record.nickName=this.user.name
    this.record.location='收藏夹'
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
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