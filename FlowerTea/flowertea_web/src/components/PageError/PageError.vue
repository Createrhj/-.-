<template>
  <div class="container">
    <div class="http404">
      <div class="pic-404">
        <img class="pic-404__parent" src="../../assets/images/404.png" alt="404">
      </div>

      <div class="bullshit">
        <div class="bullshit_oops">OOPS!</div>
        <div class="bullshit_info">All rights reserved
          <a style="color:#20a0ff">wallstreetcn</a>
        </div>
        <div class="bullshit__headline">{{ message }}</div>
        <div class="bullshit__info">Please check that the URL you entered is correct, or click the button below to return to the homepage.</div>
        <a href="http://localhost:8080/" class="bullshit__return-home">Back to home</a>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'PageError',
  computed: {
    message() {
      return 'The webmaster said that you can not enter this page...'
    }
  }
}
</script>

<style scoped>
.container{
  transform: translate(-50%,-50%);
  position: relative;
  top: 50%;
  left: 50%;
}
.pic-404{
  position: absolute;
  width: 1000px;
  padding: 0 50px;
  margin-top: 50px;
  margin-left: 80px;
  overflow: hidden;
  float: left;
}

.bullshit{
  position:relative;
  float: right;
  width: 300px;
  overflow: hidden;
  margin-right: 250px;
  margin-top: 200px;
}
.bullshit_oops {
   font-size: 50px;
   font-weight: bold;
   color: #1482f0;
   margin-bottom: 30px;
 }

.bullshit__headline {
  font-size: 20px;
  line-height: 24px;
  color: #222;
  font-weight: bold;
  margin-bottom: 30px;
  margin-top: 20px;
}
.bullshit__info {
  font-size: 13px;
  line-height: 21px;
  color: grey;
  margin-bottom: 50px;
}
.bullshit__return-home {
  display: block;
  float: left;
  width: 110px;
  height: 36px;
  background: #1482f0;
  border-radius: 100px;
  text-align: center;
  color: #ffffff;
  font-size: 14px;
  line-height: 36px
}

</style>

