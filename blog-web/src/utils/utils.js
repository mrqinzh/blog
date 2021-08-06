// 非空验证
export const isNotNullORBlank = (...args)=> {
  for (var i = 0; i < args.length; i++) {
    var argument = args[i];
    if (argument == null || argument == '' || argument == undefined) {
      return false;
    }
  }
  return true;
}

// 判断当前用户是否登录
export const logOrNot = () => {
  var token = localStorage.getItem("token");
  // console.log(token)
  if(token === null) {
    return false;
  }else{
    return true;
  }
}