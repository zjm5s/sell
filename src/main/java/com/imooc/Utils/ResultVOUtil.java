package com.imooc.Utils;

import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;

import java.util.List;

public class ResultVOUtil {
    public static ResultVO successe(Object object){
        ResultVO resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
