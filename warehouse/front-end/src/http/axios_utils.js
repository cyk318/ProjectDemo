import axios from "axios";
import {ElMessage} from "element-plus";

const ax = axios.create({
    baseURL: "http://localhost:9000",
    timeout: 5000
});

ax.interceptors.response.use((success => {
    //业务逻辑处理...
    console.log(success);
    if (success.data.code !== 0) {
        ElMessage.error(`操作失败！原因: ${success.data.msg}`)
        return
    }
    return success;
}), error => {
    console.log(error)
    ElMessage.error('服务器错误！')
    //将异常传递给下一个处理
    return Promise.reject(error);
})

export default ax;