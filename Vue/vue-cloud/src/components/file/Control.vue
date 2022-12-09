<template>
  <el-container class="main">
    <el-main>
      <div class="boxx">
        <div>
          <div class="huan">
            <ve-ring
              :data="retained"
              :legend-visible="false"
              :settings="ringextend"
              :colors="ringcolor"
              :title="cpu"
            ></ve-ring>
          </div>
          <div class="huan">
            <ve-ring
              :data="memory"
              :legend-visible="false"
              :settings="ringextend"
              :colors="ringcolor"
              :title="mem"
            ></ve-ring>
          </div>
        </div>
        <div class="xian" v-for="item in Rate" :key="item.id">
          {{item.data.title.text}}
          <ve-line width="300px" height="300px" :data="item.data" :settings="chartSettings"></ve-line>
        </div>
        <div>
          <table border="1">
            <tr><th rowspan="4" width="30%">CPU</th><th>核心线程数</th><td>{{CPU.cpuNum}}</td></tr>
            <tr><th width="45%">CPU总的使用率</th><td>{{CPU.total}}</td></tr>
            <tr><th width="45%">CPU系统使用率</th><td>{{CPU.sys}}</td></tr>
            <tr><th width="45%">CPU用户使用率</th><td>{{CPU.used}}</td></tr>
          </table>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {Toast} from 'vant'
import axios from 'axios'
import 'echarts/lib/component/title'
const requests = axios.create({baseURL: '/api'})
export default {
  name: 'Control',
  data () {
    this.chartSettings = {}
    this.gradient = ['#5BA3ED', '#32E380']
    this.cpu = {
      text: 'CUP占用率',
      left: 'center',
      top: 'center',
      itemGap: 18,
      textStyle: {
        fontFamily: '微软雅黑',
        fontSize: 20,
        fontWeight: 'bolder'
      }
    }
    this.mem = {
      text: '内存占用率',
      left: 'center',
      top: 'center',
      itemGap: 18,
      textStyle: {
        fontFamily: '微软雅黑',
        fontSize: 20,
        fontWeight: 'bolder'
      }
    }
    this.ringextend = {
      radius: ['60', '100'],
      label: {
        normal: {
          show: false,
          position: 'inside'
        }
      },
      labelLine: {
        normal: {
          label: {
            show: false,
            formatter: '({d}%)'
          },
          labelLine: { show: true }
        }
      },
      textStyle: {
        color: '#ffffff'
      }
    }
    this.ringcolor = ['#395667', '#32E380']
    return {
      CPU: {used: 0, free: 100},
      MEM: {used: 0, free: 100},
      retained: {
        columns: ['留存', '未完成'],
        rows: [{ 留存: '已使用cpu', 未完成: 0 }, { 留存: '空闲cpu', 未完成: 100 }]
      },
      memory: {
        columns: ['留存', '未完成'],
        rows: [{ 留存: '已使用内存', 未完成: 0 }, { 留存: '空闲内存', 未完成: 100 }]
      },
      Rate: [{data: {
        title: {
          text: '/api/consumer/cpu'
        },
        columns: ['时间', '访问量'],
        rows: [
          {'时间': '3分45秒', '访问量': 1393},
          {'时间': '3分47秒', '访问量': 3530},
          {'时间': '3分49秒', '访问量': 2923},
          {'时间': '3分51秒', '访问量': 1723},
          {'时间': '3分53秒', '访问量': 3792}
        ]}}],
      mm: new Map(),
      interval: 8000
    }
  },
  created () {
    this.getRate()
    this.getCUP()
    this.getMem()
  },
  methods: {
    async getCUP () {
      let token = this.$session.get('token')
      await requests.get('/consumer/getCpu', {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.retained.rows = [{ 留存: '已使用cpu', 未完成: res.data.data.used + res.data.data.sys }, { 留存: '空闲cpu', 未完成: res.data.data.free }]
          this.CPU = res.data.data
        } else {
          Toast.fail(res.data.message)
        }
      }, (e) => {
        console.log(e)
      })
      setTimeout(this.getCUP, this.interval)
    },
    async getMem () {
      let token = this.$session.get('token')
      await requests.get('/consumer/getMem', {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          this.memory.rows = [{ 留存: '已使用内存', 未完成: res.data.data.used }, { 留存: '空闲内存', 未完成: res.data.data.free }]
          this.MEM = res.data.data
        } else {
          Toast.fail(res.data.message)
        }
      }, (e) => {
        console.log(e)
      })
      setTimeout(this.getMem, this.interval)
    },
    async getRate () {
      let token = this.$session.get('token')
      await requests.get('/consumer/getRate', {headers: {'token': token}}).then((res) => {
        if (res.data.code === 200) {
          let myDate = new Date()
          let min = myDate.getMinutes()
          let sec = myDate.getSeconds()
          for (let i in res.data.data) {
            if (this.mm.get(i) === undefined) {
              this.mm.set(i, {data: {
                title: {
                  text: i
                },
                columns: ['时间', '访问量'],
                rows: [
                  {'时间': min + '分' + sec + '秒', '访问量': res.data.data[i]}
                ]}})
            } else {
              if (this.mm.get(i).data.rows.length >= 5) {
                this.mm.get(i).data.rows.shift()
              }
              this.mm.get(i).data.rows.push({'时间': min + '分' + sec + '秒', '访问量': res.data.data[i]})
            }
          }
          let rr = []
          this.mm.forEach(function (item) {
            let k = 0
            for (let i of item.data.rows) {
              if (i['访问量'] !== 0) {
                k++
              }
            }
            if (k !== 0) {
              rr.push(item)
            }
          })
          this.Rate = rr
        } else {
          Toast.fail(res.data.message)
        }
      }, (e) => {
        console.log(e)
      })
      setTimeout(this.getRate, this.interval)
    }
  }
}
</script>

<style scoped>
.boxx{
  border: 1px solid #F0F8FF;
  position: absolute;
  top: 50px;
  left: 20px;
  right: 10px;
  bottom: 10px;
}
.main{
  position: absolute;
  top: 70px;
  left: 200px;
  right: 0;
  bottom: 0;
}
.huan{
  margin-top: -120px;
  display: inline-block;
  width: 300px;
  height:300px;
  margin-bottom: 40px;
}
.huan2{
  margin-top: -120px;
  display: inline-block;
  width: 300px;
  height:300px;
  margin-bottom: 40px;
}
.xian{
  display: inline-block;
}
</style>
