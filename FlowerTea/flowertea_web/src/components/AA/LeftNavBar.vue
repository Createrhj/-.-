<template>
    <div class="bodyOfManage">
       <div class="leftNavbar" :style="{width: withOfIconType}">
<!--            头像区-->
         <div class="profileArea">
           <el-tooltip class="item" effect="dark" content="个人中心" placement="bottom">
             <img src="../../assets/images/touxiang.png" @click="personalCenterAppear">
           </el-tooltip>
         </div>

           <div class="profileArea">
               <el-tooltip class="item" effect="dark" content="个人中心" placement="bottom">
               </el-tooltip>
           </div>
<!--               导航栏区-->
           <div class="barArea">
<!--               文字导航栏-->
               <transition
                       name="animate__animated animate__bounce"
                       enter-active-class="animate__fadeInUp"
                       leave-active-class="animate__zoomOut"
                       appear
               >
             <div v-show="flagOfShow" style="width: 100%;position: absolute">
               <router-link :to="{name:'Homepage'}" active-class="active" >主页</router-link>
                 <router-link :to="{name:'Tea'}" active-class="active" >花茶</router-link>
                 <router-link :to="{name:'Material'}" active-class="active" >材料</router-link>
                 <router-link :to="{name:'Collection'}" active-class="active" >收藏夹</router-link>
                 <router-link :to="{name:'User'}" active-class="active" v-if="user.roleID!==0">用户管理</router-link>
                 <router-link :to="{name:'Record'}" active-class="active" v-if="user.roleID!==0">记录管理</router-link>
                 <router-link :to="{name:'userHome'}" active-class="active" >个人中心</router-link>
                 <router-link :to="{name:'Feedback'}" active-class="active" >意见中心</router-link>
             </div>

               </transition>
               <transition
                       name="animate__animated animate__bounce"
                       enter-active-class="animate__fadeInUp"
                       leave-active-class="animate__zoomOut"
                       appear

               >
<!--               图标导航栏-->
               <div v-show="!flagOfShow" style="width: 100%;">
                   <el-tooltip class="item" effect="dark"  content="主页" placement="right">
                   <router-link style="font-size: 20px" :to="{name:'Homepage'}"  active-class="active2"><li class="el-icon-s-home"></li></router-link>
                   </el-tooltip>
                   <el-tooltip class="item" effect="dark" content="花茶" placement="right">
                   <router-link style="font-size: 20px" :to="{name:'Tea'}" active-class="active2"><li class="el-icon-milk-tea"></li></router-link>
                   </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="花茶材料" placement="right">
                   <router-link style="font-size: 20px" :to="{name:'Material'}" active-class="active2"><li class="el-icon-takeaway-box"></li></router-link>
                   </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="收藏夹" placement="right">
                   <router-link style="font-size: 20px" :to="{name:'Collection'}" active-class="active2"><li class="el-icon-folder-opened"></li></router-link>
                    </el-tooltip>
                   <el-tooltip class="item" effect="dark" content="用户管理" placement="right" v-if="user.roleID!==0">
                   <router-link style="font-size: 20px" :to="{name:'User'}" active-class="active2"><li class="el-icon-user-solid"></li></router-link>
                   </el-tooltip>
                 <el-tooltip class="item" effect="dark" content="记录管理" placement="right" v-if="user.roleID!==0">
                   <router-link style="font-size: 20px" :to="{name:'Record'}" active-class="active2"><li class="el-icon-s-order"></li></router-link>
                 </el-tooltip>
                   <el-tooltip class="item" effect="dark" content="个人中心" placement="right">
                    <router-link style="font-size: 20px" :to="{name:'userHome'}" active-class="active2"><li class="el-icon-s-tools"></li></router-link>
                   </el-tooltip>
                 <el-tooltip class="item" effect="dark" content="意见中心" placement="right">
                   <router-link style="font-size: 20px" :to="{name:'Feedback'}" active-class="active2"><li class="el-icon-s-comment"></li></router-link>
                 </el-tooltip>
               </div>
               </transition>
           </div>
<!--           转换格式按钮-->
           <div class="changeArea">
               <li class="el-icon-guide" @click="changeToIcon"></li>
           </div>
       </div>
    </div>
</template>

<script>
    export default {
        name: "LeftNavBar",
        data(){
            return{
                withOfIconType:'100px',
                flagOfShow:true,
                flagOfPersonalCenter:true,
                user : JSON.parse(sessionStorage.getItem('CurUser')),
            }
        },
        methods:{
            changeToIcon(){
                this.flagOfShow= !this.flagOfShow
            },
            personalCenterAppear(){
            this.$bus.$emit("personalCenterChange",this.flagOfPersonalCenter)
          }
        },
    }
</script>

<style scoped>
    .active{
        background-color: rgba(37,123,94,0.2);
        color: #257B5E;
    }
    .active2{
        color: #257B5E;
    }
    a{
        position: relative;
        text-decoration: none;
        display: block;
        height: 50px;
        text-align: center;
        line-height: 50px;
        color: #3C3F41;
    }
    a::before{
        display: block;
        content: "";
        width: 3px;
        height: 50px;
        position: absolute;
        border-radius: 10px;
    }
    a:hover::before{
        background-color: #69717A;
    }
    .bodyOfManage{
        width: 100%;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .leftNavbar{
        transition: 0.5s linear;
        overflow: hidden;
        border-top-left-radius: 15px;
        border-bottom-left-radius: 15px;
        height: 650px;
        width: 100px;
        background-color: #F5F7F9;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .profileArea{

        width: 100%;
        flex: 2;
        display: flex;
        justify-content: center;
        align-items: center;

    }
    .profileArea:hover img{
        cursor: pointer;
    }
    .barArea{
        font-size: 16px;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        flex: 7;
        font-weight: bold;
        position: relative;
        margin-bottom: 60px;
    }
    .profileArea img{
        border-radius: 10px;
        width: 50px;
        height:50px;
    }
    .changeArea{
        flex: 2;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .changeArea li{
       font-size: 18px;
    }
    .changeArea li:hover{
        color: #257B5E;
        cursor: pointer;
    }


</style>