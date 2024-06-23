import {ax} from "../common/axios_handler.js";

export const getProductPage = (start, limit) => {
    return ax.get(`/product/page?start=${start}&limit=${limit}`)
        .then((ok) => {
            return ok.data.data
        })
}