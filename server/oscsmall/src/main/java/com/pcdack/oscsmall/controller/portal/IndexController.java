package com.pcdack.oscsmall.controller.portal;

import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IIndexService iIndexService;
    @RequestMapping(value = "index.do")
    @ResponseBody
    public ServerResponse getIndexInfo( @RequestParam(value = "pageNum",defaultValue = "1") int pageNum){
        return iIndexService.getIndex(pageNum);
    }
}