package com.qin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qin.pojo.*;
import com.qin.service.MsgService;
import com.qin.service.MyCommentService;
import com.qin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private MsgService msgService;

    @Autowired
    private MyCommentService myCommentService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Resp<List<MyComment>> list() {
        List<MyComment> myComments = myCommentService.preOrder();
        return Resp.success(myComments);
    }

    @GetMapping("/{id}")
    public Resp<List<MyComment>> loadComment(@PathVariable("id") int articleId) {
        return Resp.success(myCommentService.all(articleId));
    }

    @PostMapping("/add")
    public Resp<String> add(@RequestBody MyComment myComment, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        myComment.setUser_id(user.getId());
        myCommentService.addComment(myComment);

        return Resp.success("success");
    }

    /* ================================================  留言部分方法  ======================================================== */

    @PostMapping("/addMsg")
    public Resp<String> addMsg(@RequestBody LeaveMsg leaveMsg, HttpServletRequest req){
        User user = (User) req.getAttribute("user");
        leaveMsg.setUser_id(user.getId());

        int i = msgService.addMsg(leaveMsg);
        return i==1 ? new Resp<>("200","添加成功","") : Resp.error("500","添加失败");
    }

    /**
     * 分页获取所有的留言
     * @param currentPage   当前页
     * @param pageSize  每页大小
     * @return  封装返回信息  count： 总的数量     list： 查询结果集
     */
    @RequestMapping("/all/{currentPage}/{pageSize}")
    public Map<String,Object> showAll(@PathVariable("currentPage")int currentPage, @PathVariable("pageSize")int pageSize){
        Map<String,Object> map = new HashMap<>();

        PageHelper.startPage(currentPage,pageSize);
        List<LeaveMsg> allMsg = msgService.findAllMsg();
        PageInfo<LeaveMsg> pageInfo = new PageInfo<>(allMsg);
        map.put("count",pageInfo.getTotal());
        map.put("list",pageInfo.getList());

        return map;
    }

}
