<template>
  <div style="margin-left: 10px">
    <div style="margin-bottom: 10px;margin-top: 20px">
      <el-input v-model="findMaterial"
                placeholder="请输入材料名称"
                style="width: 200px"
                @keyup.enter.native="loadPost"
                suffix-icon="el-icon-search"/>
      <el-select v-model="findClassification" clearable placeholder="请选择" style="margin-left:10px;width: 100px">
        <el-option
            v-for="item in classifications"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="restParam">重置</el-button>
      <el-button type="primary" @click="add" v-if="this.user.roleID!==0">新增<i class="el-icon-upload el-icon--right"></i></el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{background: '#aeb7c5',color: '#2f2626'}"
              border
              style="overflow: auto;height: 500px;margin-bottom: 10px"
    >
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
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          type="index"
          label="序号"
          width="80">
      </el-table-column>
      <el-table-column prop="materialName" label="材料名称" width="150">
      </el-table-column>
      <el-table-column prop="classification" label="材料分类" width="100">
      </el-table-column>
      <el-table-column prop="materialFunction" label="材料作用" width="200">
      </el-table-column>
      <el-table-column prop="price" label="材料价格" width="100">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="200" v-if="this.user.roleID!==0">
        <template v-slot="scope">
          <el-button type="success" @click="update(scope.row)" style="margin-right: 10px">编辑</el-button>
          <el-popconfirm
              title="确定删除该材料信息吗？"
              @confirm="del(scope.row)"
          >
            <el-button slot="reference" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog title="添加材料信息"
        :visible.sync="addDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="form.materialName"></el-input>
        </el-form-item>
        <el-form-item label="材料别名" prop="nickName">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="材料分类" prop="classification">
          <el-input v-model="form.classification"></el-input>
        </el-form-item>
        <el-form-item label="材料产地" prop="production">
          <el-input v-model="form.production"></el-input>
        </el-form-item>
        <el-form-item label="材料组成" prop="composition">
          <el-input v-model="form.composition"></el-input>
        </el-form-item>
        <el-form-item label="材料功效" prop="materialFunction">
          <el-input v-model="form.materialFunction"></el-input>
        </el-form-item>
        <el-form-item label="材料性味" prop="flavor">
          <el-input v-model="form.flavor"></el-input>
        </el-form-item>
        <el-form-item label="材料价格" prop="price">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确定</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
    </el-dialog>

    <el-dialog title="修改材料信息"
               :visible.sync="updateDialogVisible"
               width="30%"
               center>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="材料名称" prop="materialName">
          <el-input v-model="form.materialName"></el-input>
        </el-form-item>
        <el-form-item label="材料别名" prop="nickName">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="材料分类" prop="classification">
          <el-input v-model="form.classification"></el-input>
        </el-form-item>
        <el-form-item label="材料产地" prop="production">
          <el-input v-model="form.production"></el-input>
        </el-form-item>
        <el-form-item label="材料组成" prop="composition">
          <el-input v-model="form.composition"></el-input>
        </el-form-item>
        <el-form-item label="材料功效" prop="materialFunction">
          <el-input v-model="form.materialFunction"></el-input>
        </el-form-item>
        <el-form-item label="材料性味" prop="flavor">
          <el-input v-model="form.flavor"></el-input>
        </el-form-item>
        <el-form-item label="材料价格" prop="price">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateMaterial">修改</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
     </span>
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
  name: "MaTerial",
  data() {
    let checkMaterialName=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料名称'));
      } else if (value.length>8) {
        callback(new Error('花茶名称范围1~8'));
      } else {
        callback();
      }
    };
    let checkNickName=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料别名'));
      } else if (value.length>10) {
        callback(new Error('材料别名1~10'));
      } else {
        callback();
      }
    };
    let checkClassification=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料类别'));
      } else if (value.length>2) {
        callback(new Error('材料类别范围1~2'));
      } else {
        callback();
      }
    };
    let checkProduction=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料产地'));
      } else if (value.length>10) {
        callback(new Error('材料别名1~10'));
      } else {
        callback();
      }
    };
    let checkComposition=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料组成'));
      } else if (value.length>15) {
        callback(new Error('材料组成范围1~15'));
      } else {
        callback();
      }
    };
    let checkMaterialFunction=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料功效'));
      } else if (value.length>50) {
        callback(new Error('材料功效范围1~50'));
      } else {
        callback();
      }
    };
    let checkFlavor=(rule,value,callback)=>{
      if (value === '') {
        callback(new Error('请输入材料性味'));
      } else if (value.length>10) {
        callback(new Error('材料性味范围1~10'));
      } else {
        callback();
      }
    };

    return {
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      findMaterial:'',
      findClassification:'',
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
      addDialogVisible:false,
      updateDialogVisible:false,
      classifications:[{
          value: '固体',
          label: '固体'
        }, {
          value: '干货',
          label: '干货'
        },{
          value: '鲜货',
          label: '鲜货'
        }],
      form:{
        materialID:'',
        materialName:'',
        nickName:'',
        classification:'',
        production:'',
        composition:'',
        materialFunction:'',
        flavor:'',
        price:'',
      },
      rules: {
        materialName: [
          {validator: checkMaterialName, required: true, trigger: "blur" }
        ],
        nickName: [
          {validator: checkNickName, required: true, trigger: "blur" }
        ],
        classification: [
          {validator: checkClassification, required: true, trigger: "blur" }
        ],
        production: [
          {validator: checkProduction, required: true, trigger: "blur" }
        ],
        composition: [
          {validator: checkComposition, required: true, trigger: "blur" }
        ],
        materialFunction: [
          {validator: checkMaterialFunction, required: true, trigger: "blur" }
        ],
        flavor: [
          {validator: checkFlavor, required: true, trigger: "blur" }
        ],
        price: [
          { required: true, message: '请选输入材料价格', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    del(row){
      this.$axios.get("http://localhost:8081/material/delete?materialID="+row.materialID).then(res=>res.data).then(res=>{
        if(res.code===888){
          this.$message({
            showClose: true,
            message: '删除材料信息成功',
            type: 'success'
          });
          this.record.id=row.materialID
          this.record.name=row.materialName
          this.record.operation='删除材料'
          this.$axios.post("http://localhost:8081/record/save",this.record)
          this.loadPost()
        }else{
          this.$message({
            showClose: true,
            message: '删除材料信息失败',
            type: 'error'
          });
        }
      })
    },
    update(row){
      this.updateDialogVisible=true
      this.$nextTick(()=>{
        this.form.materialID=row.materialID
        this.form.materialName=row.materialName
        this.form.nickName=row.nickName
        this.form.classification=row.classification
        this.form.production=row.production
        this.form.composition=row.composition
        this.form.materialFunction=row.materialFunction
        this.form.flavor=row.flavor
        this.form.price=row.price
      })
    },
    updateMaterial(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post('http://localhost:8081/material/update', this.form).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '修改材料信息成功',
                type: 'success'
              });
              this.record.id=this.form.teaID
              this.record.name=this.form.teaName
              this.record.operation='修改材料'
              this.$axios.post("http://localhost:8081/record/save",this.record)
              this.updateDialogVisible=false
              this.loadPost()
            } else {
              this.$message({
                showClose: true,
                message: '修改材料信息失败',
                type: 'error'
              });
            }
          })
        }
        else {
          this.$message({
            showClose: true,
            message: '请完整填写材料信息',
            type: 'warning'
          });
        }
      });
    },
    add(){
      this.addDialogVisible=true
      this.resetForm('form')
      console.log(this.form)
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post("http://localhost:8081/material/select",this.form).then(res => res.data).then(res => {
            if (res.code === 888) {
              this.$message({
                showClose: true,
                message: '材料已经存在',
                type: 'warning'
              });
            } else {
              this.$axios.post('http://localhost:8081/material/save', this.form).then(res => res.data).then(res => {
                if (res.code === 888) {
                  this.$message({
                    showClose: true,
                    message: '新增材料信息成功',
                    type: 'success'
                  });
                  this.record.id=this.form.materialID
                  this.record.name=this.form.materialName
                  this.record.operation='新增材料'
                  this.$axios.post("http://localhost:8081/record/save",this.record)
                  this.addDialogVisible = false
                  this.loadPost()
                } else {
                  this.$message({
                    showClose: true,
                    message: '新增材料信息失败',
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
            message: '请完整填写材料信息',
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
    restParam(){
      this.findMaterial=''
      this.findClassification=''
    },
    loadPost(){
      this.$axios.post('http://localhost:8081/material/selectPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          materialName:this.findMaterial,
          classification:this.findClassification
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
    this.record.location='材料管理'
  }

}
</script>

<style>
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