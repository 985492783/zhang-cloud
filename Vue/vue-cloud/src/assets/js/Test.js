export const Response = function (_this, obj) {
  switch (obj.type) {
    case TYPE.NOTIFY: NOTIFY(_this, obj)
      break
    case TYPE.WARNING: WARNING(_this, obj)
      break
    case TYPE.ORDER: ORDER(_this, obj)
  }
}
const NOTIFY = function (_this, obj) {
  _this.$router.push({name: 'orderFinish', params: {data: obj.data}})
}
const WARNING = function (_this, obj) {
}
const ORDER = function (_this, obj) {
}

const TYPE = {
  NOTIFY: 'NOTIFY',
  WARNING: 'WARNING',
  ORDER: 'ORDER'
}
