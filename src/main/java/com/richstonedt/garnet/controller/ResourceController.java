package com.richstonedt.garnet.controller;


import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ModuleResourceView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.service.ResourceService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <b><code>ResourceController</code></b>
 * <p/>
 * Resource的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Mar 13 12:29:22 CST 2018.
 *
 * @author maxuepeng
 * @version 1.0.0
 *
 */
@Api(value = "[Garnet]资源接口")
@RestController
@LoginRequired
@RequestMapping(value = "/api/v1.0")
public class ResourceController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ResourceController.class);

    /** The service. */
    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "[Garnet]创建资源", notes = "创建一个资源")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createResource(
            @ApiParam(value = "资源", required = true) @RequestBody ResourceView resourceView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            Long id = resourceService.insertResource(resourceView);
            // 获取刚刚保存的实体
            Resource resource = resourceService.selectByPrimaryKey(id);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/resources/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<Resource> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, resource);
            return new ResponseEntity<>(garnetMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除资源", notes = "通过id删除资源")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteResource(
            @ApiParam(value = "资源id", required = true) @PathVariable(value = "id") Long id) {
        try {
            resourceService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<ResourceView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除资源", notes = "批量删除资源")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteResources(
            @ApiParam(value = "资源ids，样例 - 1,2,3", required = true) @RequestParam String ids) {
        try {

            ResourceView resourceView = new ResourceView();
            Resource resource = new Resource();
            for (String id : ids.split(",")) {
                resource.setId(Long.parseLong(id));
                resourceView.setResource(resource);
                resourceService.deleteByPrimaryKey(Long.parseLong(id));
            }
            // 封装返回信息
            GarnetMessage<ResourceView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]更新资源", notes = "更新资源信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/resources", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateResources(
            @ApiParam(value = "资源信息", required = true) @RequestBody ResourceView resourceView) {
        try {

            resourceService.updateResource(resourceView);
            // 封装返回信息
            GarnetMessage<ResourceView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, resourceView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个资源", notes = "通过id获取资源")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResource(
            @ApiParam(value = "资源id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final Resource resource = resourceService.selectByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<Resource> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, resource);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取资源列表", notes = "通过查询条件获取资源列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResources(
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "应用ID", defaultValue = "", required = false) @RequestParam(value = "applicationId", defaultValue = "", required = false) Long applicationId,
            @ApiParam(value = "租户ID", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "类型名称", defaultValue = "", required = false) @RequestParam(value = "type", defaultValue = "", required = false) String type,
            @ApiParam(value = "用户Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "", required = false) Integer enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) Integer pageSize) {
        try {

            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setPageSize(pageSize);
            resourceParm.setUserId(userId);
            resourceParm.setType(type);
            resourceParm.setApplicationId(applicationId);
            resourceParm.setTenantId(tenantId);
            resourceParm.setSearchName(searchName);
            resourceParm.setPageNumber(pageNumber);

            PageUtil pageInfo = resourceService.getResourcesByParams(resourceParm);
            // 封装返回信息
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取菜单控制appCode资源列表", notes = "获取菜单控制appCode资源列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/getappcode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAppCodeResources(
            @ApiParam(value = "用户Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId) {
        try {

            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setUserId(userId);

            String jsonString = resourceService.getGarnetAppCodeResources(resourceParm);
            // 封装返回信息
            return new ResponseEntity<>(jsonString, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据应用和类型获取资源列表", notes = "根据应用和类型获取资源列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/byappandtype", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResourcesByAppAndType(
            @ApiParam(value = "appId", defaultValue = "", required = false) @RequestParam(value = "applicationId", defaultValue = "", required = false) Long applicationId ,
            @ApiParam(value = "type", defaultValue = "", required = false) @RequestParam(value = "type", defaultValue = "", required = false) String type ,
            @ApiParam(value = "用户Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId) {
        try {

            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setUserId(userId);
            resourceParm.setApplicationId(applicationId);
            resourceParm.setType(type);

            String result = resourceService.getAllResourceByAppAndType(resourceParm);
            // 封装返回信息
            GarnetMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取菜单资源列表", notes = "获取菜单资源列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/getsysmenu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysMenuResources(
            @ApiParam(value = "用户Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId) {
        try {

            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setUserId(userId);

//            String jsonString = resourceService.getGarnetSysMenuResources(resourceParm);
            ModuleResourceView moduleResourceView = resourceService.getModuleResources(resourceParm);
            // 封装返回信息
//            return new ResponseEntity<>(jsonString, HttpStatus.OK);
            return new ResponseEntity<>(moduleResourceView, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]验证资源配置类型是否有关联资源", notes = "验证资源配置类型是否有关联资源")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/relate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> hasRelated(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {
            boolean b = resourceService.hasRelated(ids);
            // 封装返回信息
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据登录用户和权限匹配路径查询该用户新增页的类型选项", notes = "根据登录用户和权限匹配路径查询该用户新增页的类型选项")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/gettype", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTypeByUserIdAdnPath(
            @ApiParam(value = "用户Id", defaultValue = "", required = true) @RequestParam(value = "userId", defaultValue = "", required = true) Long userId,
            @ApiParam(value = "path", defaultValue = "", required = true) @RequestParam(value = "path", defaultValue = "", required = true) String path) {
        try {
            String type = resourceService.getTypeByUserIdAndPath(userId, path);
            // 封装返回信息
            GarnetMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, type);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取默认关联所有用户选项配置级别", notes = "获取默认关联所有用户选项配置级别")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/getrelateduserlevel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRelateAllUserLevelByUserIdAdnPath(
            @ApiParam(value = "用户Id", defaultValue = "", required = true) @RequestParam(value = "userId", defaultValue = "", required = true) Long userId,
            @ApiParam(value = "path", defaultValue = "", required = true) @RequestParam(value = "path", defaultValue = "", required = true) String path) {
        try {
            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setUserId(userId);
            resourceParm.setResourcePathWildCard(path);
            Integer relatedAllUsersLevel = resourceService.isRelatedAllUsers(resourceParm);
            // 封装返回信息
            GarnetMessage<Integer> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, relatedAllUsersLevel);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取用户权限行为", notes = "获取用户权限行为")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resources/getuseraction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getPermissionActionByUserIdAdnPath(
            @ApiParam(value = "用户Id", defaultValue = "", required = true) @RequestParam(value = "userId", defaultValue = "", required = true) Long userId,
            @ApiParam(value = "path", defaultValue = "", required = true) @RequestParam(value = "path", defaultValue = "", required = true) String path) {
        try {
            ResourceParm resourceParm = new ResourceParm();
            resourceParm.setUserId(userId);
            resourceParm.setResourcePathWildCard(path);
            String action = resourceService.getUserPermissionAction(resourceParm);
            // 封装返回信息
            GarnetMessage<String> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, action);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
