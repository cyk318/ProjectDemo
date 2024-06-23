import axios from "axios";

export let ax = axios.create({
    baseURL: "http://localhost:10010",
    timeout: 5000
});
ax.interceptors.response.use(function (response) { //自定义响应参数名
    console.log(response);
    if (response.data.code !== 0) {
        alert(`服务器出现错误 ex: ${response.data.msg}`);
    }
    //其他错误处理...
    return response; //这里必须返回 response，否则报错
});