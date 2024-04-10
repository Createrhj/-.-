<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input v-model="userName"
                placeholder="请输入用户名"
                style="width: 200px"
                @keyup.enter.native="loadPost"
                suffix-icon="el-icon-search"></el-input>
      <el-select v-model="roleID"  clearable placeholder="请选择" style="margin-left:10px;width: 100px">
        <el-option
            v-for="item in roleIDs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="location" clearable placeholder="请选择" style="margin-left:10px;width: 150px">
        <el-option
            v-for="item in locations"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
    </div>
    <el-table :data="tableData"
            :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
            border
            style="height: 518px;overflow: auto"
  >
    <el-table-column type="expand" width="50">
      <template v-slot="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="用户昵称">
            <span>{{ props.row.nickName }}</span>
          </el-form-item>
          <el-form-item label="用户名">
            <span>{{ props.row.userName}}</span>
          </el-form-item>
          <el-form-item label="时间">
            <span>{{ props.row.time }}</span>
          </el-form-item>
          <el-form-item label="管理内容">
            <span>{{ props.row.location }}</span>
          </el-form-item>
          <el-form-item label="对象编号">
            <span>{{ props.row.id }}</span>
          </el-form-item>
          <el-form-item label="对象名称">
            <span>{{ props.row.name }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
        type="index"
        label="序号"
        width="50px">
    </el-table-column>
    <el-table-column prop="roleID" label="用户权限" width="150">
      <template v-slot="scope">
        <div class="icon-size" style="font-size: 25px">
          <i class="el-icon-s-custom" v-if="scope.row.roleID === 1">
            <el-tag type="danger" style="margin: 5px">管理员</el-tag>
          </i>
          <i class="el-icon-user-solid" v-if="scope.row.roleID === 0">
            <el-tag style="margin:5px">用户</el-tag>
          </i>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="time" label="时间" width="200">
    </el-table-column>
    <el-table-column prop="location" label="页表" width="140">
    </el-table-column>
    <el-table-column prop="operation" label="用户行为" width="130">
    </el-table-column>
    <el-table-column prop="operate" label="操作" width="150">
      <template v-slot="scope">
        <el-popconfirm
            title="确定删除该记录信息吗？"
            @confirm="del(scope.row)"
        >
          <el-button slot="reference" type="danger">删除</el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
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
  name: "ReCord",
  data(){
    return{
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      userName:'',
      roleID:'',
      location:'',
      roleIDs:[
        {
          value: '0',
          label: '用户'
        },
        {
          value: '1',
          label: '管理员'
        }
      ],
      locations:[
        {
          value: '花茶管理',
          label: '花茶管理'
        },
        {
        value: '材料管理',
        label: '材料管理'
        },
        {
          value: '花茶配比管理',
          label: '花茶配比管理'
        },
        {
          value: '用户管理',
          label: '用户管理'
        },
        {
          value: '收藏夹',
          label: '收藏夹'
        },
      ],
    }
  },
  methods:{
    del(row){
      this.$axios.post("http://localhost:8081/record/delete",row).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除记录信息成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            showClose: true,
            message: '删除记录信息失败',
            type: 'error'
          });
        }
      })
    },
    restParam(){
      this.userName=''
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
      this.$axios.post('http://localhost:8081/record/selectPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          userName:this.userName,
          roleID:this.roleID,
          location:this.location
        }
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