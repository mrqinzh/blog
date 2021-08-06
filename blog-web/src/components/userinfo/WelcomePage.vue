<template>
  <div id="welcomepage" v-loading="loading" class="animate__animated animate__fadeIn">
    <!-- 资料展示部分 -->
    <div>
      <div>
        <h1 style="float: left;margin: 10px 0 10px 10px">
          <i class="layui-icon layui-icon-link"></i>
          <span style="margin-left: 5px">资料展示</span>
        </h1>
        <div style="float: right;margin: 20px 10px 0 0;font-size: 20px;cursor: pointer;color: #409EFF">
          <i class="el-icon-edit"></i>
        </div>
      </div>
      <div style="padding: 10px;background: #fff;">
        <div class="layui-row layui-col-space15">
          <!-- 左边面板 -->
          <div class="layui-col-md4">
            <div class="layui-panel">
              <div style="padding: 5px 15px;float: left">
                <i class="layui-icon layui-icon-about" style="padding-right: 5px"></i>
                <span>基础信息</span>
              </div>
              <hr>
              <div style="float: left;">
                <div style="margin-top: 10px">年龄：{{age}}</div>
                <div style="margin-top: 20px">性别：{{sex}}</div>
                <div style="margin: 20px 0 0 26px">所在地：{{address}}</div>
              </div>
            </div>
          </div>
          <!-- 中间面板 -->
          <div class="layui-col-md4">
            <div class="layui-panel">
              <div style="padding: 5px 15px;float: left">
                <i class="layui-icon layui-icon-search" style="padding-right: 5px"></i>
                <span>联系方式</span>
              </div>
              <hr>
              <div style="float: left;margin-left: 20px">
                <div style="margin-top: 10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel：{{tel}}</div>
                <div style="margin-top: 20px">&nbsp;&nbsp;&nbsp;&nbsp;QQ：{{qq}}</div>
                <div style="margin-top: 20px">VX：{{vx}}</div>
              </div>
            </div>   
          </div>
          <!-- 右边面板 -->
          <div class="layui-col-md4">
            <div class="layui-panel">
              <div style="padding: 5px 15px;float: left">
                <i class="layui-icon layui-icon-note" style="padding-right: 5px"></i>
                <span>我的技能</span>
              </div>
              <hr>
              <div style="float: left;">
                <div>
                  <span style="margin-top: 10px">职业技能：</span>
                  <span style="margin-top: 10px;font-size: 15px">{{mySkill}}</span>
                </div>
              </div>
            </div>   
          </div>
        </div> 
      </div>
    </div>
    <hr>
    <!-- 折线图 -->
    <div>
      <div id="photo" style="width: 100%;height:380px;"></div>
    </div>
    
  </div>
  
</template>

<script>
export default {
  data() {
    return {
      age: '20',
      sex: '男',
      address: '重庆',
      tel: '15730567860',
      qq: '1552589784',
      vx: 'qzh09010',
      mySkill: 'JavaSE、Html、CSS、JavaScript、MySQL、JavaWeb、Mybatis、Spring、SpringMVC、SpringBoot、SpringCloud、JVM、Linux、Redis、Vue',
      loading: true,
    }
  },
  // 折线图
  mounted() {
    let echarts = require('echarts');
    var myChart = echarts.init(document.getElementById('photo'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '一周的生活'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['看书','休闲','看视频','敲代码']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        // 头部工具栏
        toolbox: {
            feature: {
                //saveAsImage: {}  // 下载保存图片
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一','周二','周三','周四','周五','周六','周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:'敲代码',
                type:'line',
                stack: '总量',
                data:[2, 5, 4, 6, 5, 6, 2]
            },
            {
                name:'看书',
                type:'line',
                stack: '总量',
                data:[5, 6, 4, 8, 10, 4, 5]
            },
            {
                name:'休闲',
                type:'line',
                stack: '总量',
                data:[10, 4, 8, 9, 12, 11, 10]
            },
            {
                name:'看视频',
                type:'line',
                stack: '总量',
                data:[10, 12, 11, 11, 12, 5, 6]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    this.loading = false;
  },
  
}
</script>

<style>
  #welcomepage {
   margin: 0px 10px 2px 10px;
 }

 .box-card {
    width: 200px;
    height: 250px;
    
  }

  .layui-panel {
    height: 200px;
    border: 1px solid #EBEEF5;
    background: #fff;
  }
  .layui-panel:hover {
    cursor: pointer;
    color: #1fab89;
  }
</style>