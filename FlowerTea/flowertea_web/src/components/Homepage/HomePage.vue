<template>
  <div>
    <div>

    <div style="margin-top: 20px;margin-left: 50px;float: left">
      <el-card :body-style="{ padding: '0px' }" style="width: 200px;height: 270px">
        <img src="../../assets/images/tea07.webp" class="image">
        <div style="padding: 8px">
          <span>茉莉花茶</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
          </div>
        </div>
      </el-card>
    </div>

    <div style="margin-top: 20px;margin-left: 100px;float: left">
      <el-card :body-style="{ padding: '0px' }" style="width: 200px;height: 270px">
        <img src="../../assets/images/tea08.jpeg" class="image">
        <div style="padding: 8px">
          <span>玫瑰花茶</span>
          <div class="bottom clearfix">
            <time class="time">{{ currentDate }}</time>
          </div>
        </div>
      </el-card>
    </div>
      <div style="margin-top: 20px;margin-right:50px;float: right">
        <el-card :body-style="{ padding: '0px' }" style="width: 200px;height: 270px">
          <img src="../../assets/images/tea09.jpg" class="image">
          <div style="padding: 10px">
            <span>玫瑰白茶</span>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  <div style="margin-top: 20px;width: 800px;margin-left: 50px;float:left" >
  <el-carousel :interval="4000" width="500px" type="card" height="250px" indicator-position="outside">
    <el-carousel-item v-for="item in carouseData" :key="item.id">
      <img width="500px" height="300px" :src="item.url" alt="" />
    </el-carousel-item>
  </el-carousel>
  </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      currentDate: new Date(),
      //定义跑马灯的图片路径
      carouseData: [
        { id:1,url: require("../../assets/images/tea01.jpg")  },
        { id:2,url: require("../../assets/images/tea02.jpg") },
        { id:3,url: require("../../assets/images/tea03.jpg") },
        { id:4,url: require("../../assets/images/tea04.webp") },
        { id:5,url: require("../../assets/images/tea05.jpg") },
        { id:6,url: require("../../assets/images/tea06.jpg") },
      ],
    };
  },
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}
.image {
  width: 100%;
  display: block;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>