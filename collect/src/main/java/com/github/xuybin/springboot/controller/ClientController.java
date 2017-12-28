package com.github.xuybin.springboot.controller;

import com.github.xuybin.springboot.model.*;
import com.github.xuybin.springboot.service.ClientService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"用户管理"}, description = "新增 修改 删除等")
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;


    @ApiOperation(value = "条件查询", tags={ "用户管理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Paginator.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Paginator<List<ClientInfo>>> getClient(
            @ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization",required = false) String _,
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageIndex", required = true) Integer pageIndex,
            @ApiParam(value = ",页大小", required = true)@RequestParam(value = "pageSize", required = true)Integer pageSize
    )throws ErrThrowable {
        List<ClientInfo> clientInfoList=new ArrayList<>();
        Paginator<List<ClientInfo>> paginator=new Paginator(clientInfoList);
        try {
            paginator.setTotalCount(clientService.getPaginatorTotalCount());
            paginator.setPageIndex(pageIndex);
            paginator.setPageSize(pageSize);
            paginator.setTotalPages((int)(Math.ceil((double)paginator.getTotalCount()/(double)pageSize)));
            clientInfoList.addAll(clientService.getPaginator((pageIndex-1)*pageSize,pageSize));
        } catch (Throwable t) {
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
        return  ResponseEntity.ok().body(paginator);
    }

    @ApiOperation(value = "修改", tags={ "用户管理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Integer> putClient(
            @ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization") String _,
            @ApiParam(value = "客户端信息", required = true) @RequestBody ClientInfo clientInfo
    )throws ErrThrowable {
        Integer count=0;
        try {
            count+=clientService.update(clientInfo);
        } catch (Throwable t) {
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
        return  ResponseEntity.ok().body(count);
    }

    @ApiOperation(value = "新增", tags={ "用户管理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Integer> postClient(
            @ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization") String _,
            @ApiParam(value = "客户端信息列表", required = true) @RequestBody List<ClientInfo> clientInfoList)throws ErrThrowable {
        Integer count=0;
        try {
            count+=clientService.insert(clientInfoList);
        } catch (Throwable t) {
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
        return  ResponseEntity.ok().body(count);
    }


    @ApiOperation(value = "删除", tags={ "用户管理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})
    @RequestMapping(value = "/{client_id:.+}", method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteClient(
            @ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization") String _,
            @PathVariable("client_id") String client_id)throws ErrThrowable {
        Integer count=0;
        try {
            count+=clientService.delete(client_id);
        } catch (Throwable t) {
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
        return  ResponseEntity.ok().body(count);
    }
}
