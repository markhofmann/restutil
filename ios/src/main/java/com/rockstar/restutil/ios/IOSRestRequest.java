package com.rockstar.restutil.ios;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestUtil;

/*-[
  #import <NSDictionaryMap.h>
  #import <java/util/HashMap.h>
  #import <java/util/Iterator.h>
  #import <UNIRest.h>
  ]-*/


/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestRequest")
public class IOSRestRequest extends BaseRestRequest {
    public IOSRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        super(requestMethod, url, restUtil);
    }

    /**
     * Native implementation of the execute method.
     * See {@link com.rockstar.restutil.common.RestRequest#execute(RestCallback)} for details.
     */
    @Override
    public native <T, E> void execute(RestCallback<T, E> callback) /*-[
            NSDictionary* headers = @{@"accept": @"application/json"};
            //NSDictionary* parameters = @{@"origin": @"35.776,51.464", @"destination": @"35.782776,51.433414"};
            
            // UNIHTTPJsonResponse *response = [[UNIRest get:^(UNISimpleRequest *request) {
            //     [request setUrl:@"https://maps.googleapis.com/maps/api/directions/json"];
            //     [request setHeaders:headers];
            //     [request setParameters:parameters];
            // }] asJson];

            //Translate headers to NSDictionary
            // JavaUtilHashMap *headerMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:headers_];
            // NSMutableDictionary *headerDictionary = [[NSMutableDictionary alloc] init];
            // id <JavaUtilIterator> iterator = [headerMap newKeyIterator];
            // while ([iterator hasNext]) {
            //     id object = [iterator next];
            //     [headerDictionary setObject:[headerMap getWithId:object] forKey:object];
            // }

            //Translate parameters_ to NSDictionary
            JavaUtilHashMap *parameterMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:parameters_];
            NSMutableDictionary *parameterDictionary = [[NSMutableDictionary alloc] init];
            id <JavaUtilIterator> iterator = [parameterMap newKeyIterator];
            while ([iterator hasNext]) {
                id object = [iterator next];
                [parameterDictionary setObject:[parameterMap getWithId:object] forKey:object];
            }

            if ([[[self getRequestMethod] getMethod] isEqualToString:@"GET"]) {
        [[UNIRest get:^(UNISimpleRequest *request) {
          [request setUrl:[self getUrl]];
          [request setHeaders:headers];
          [request setParameters:parameterDictionary];
        }] asJsonAsync:^(UNIHTTPJsonResponse* response, NSError *error) {
          // This is the asyncronous callback block
          NSInteger code = response.code;
          NSDictionary *responseHeaders = response.headers;
          UNIJsonNode *body = response.body;
          NSData *rawBody = response.rawBody;
          
          //              NSLog(@"%@",response.body.JSONObject);
          
          if(error) {
            NSString *errMsg = error.userInfo[@"NSLocalizedDescription"];
            [callback onNetworkErrorWithNSString:errMsg];
          } else if(code == 200){
            [callback onSuccessWithRestResponse:(RestResponse *)response];
          } else {
            [callback onErrorWithRestResponse:(RestResponse *)response];
          }
          
          // [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"POST"]) {
        [[UNIRest post:^(UNISimpleRequest *request) {
          [request setUrl:[self getUrl]];
          [request setHeaders:headers];
          [request setParameters:parameterDictionary];
        }] asJsonAsync:^(UNIHTTPJsonResponse* response, NSError *error) {
          // This is the asyncronous callback block
          NSInteger code = response.code;
          NSDictionary *responseHeaders = response.headers;
          UNIJsonNode *body = response.body;
          NSData *rawBody = response.rawBody;
          
          //              NSLog(@"%@",response.body.JSONObject);
          
          if(error) {
            NSString *errMsg = error.userInfo[@"NSLocalizedDescription"];
            [callback onNetworkErrorWithNSString:errMsg];
          } else if(code == 200){
            [callback onSuccessWithRestResponse:(RestResponse *)response];
          } else {
            [callback onErrorWithRestResponse:(RestResponse *)response];
          }

          // [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"PUT"]) {
        [[UNIRest put:^(UNISimpleRequest *request) {
          [request setUrl:[self getUrl]];
          [request setHeaders:headers];
          [request setParameters:parameterDictionary];
        }] asJsonAsync:^(UNIHTTPJsonResponse* response, NSError *error) {
          // This is the asyncronous callback block
          NSInteger code = response.code;
          NSDictionary *responseHeaders = response.headers;
          UNIJsonNode *body = response.body;
          NSData *rawBody = response.rawBody;
          
          //              NSLog(@"%@",response.body.JSONObject);
          
          if(error) {
            NSString *errMsg = error.userInfo[@"NSLocalizedDescription"];
            [callback onNetworkErrorWithNSString:errMsg];
          } else if(code == 200){
            [callback onSuccessWithRestResponse:(RestResponse *)response];
          } else {
            [callback onErrorWithRestResponse:(RestResponse *)response];
          }
          
          // [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"DELETE"]) {
        [[UNIRest delete:^(UNISimpleRequest *request) {
          [request setUrl:[self getUrl]];
          [request setHeaders:headers];
          [request setParameters:parameterDictionary];
        }] asJsonAsync:^(UNIHTTPJsonResponse* response, NSError *error) {
          // This is the asyncronous callback block
          NSInteger code = response.code;
          NSDictionary *responseHeaders = response.headers;
          UNIJsonNode *body = response.body;
          NSData *rawBody = response.rawBody;
          
          //              NSLog(@"%@",response.body.JSONObject);
          
          if(error) {
            NSString *errMsg = error.userInfo[@"NSLocalizedDescription"];
            [callback onNetworkErrorWithNSString:errMsg];
          } else if(code == 200){
            [callback onSuccessWithRestResponse:(RestResponse *)response];
          } else {
            [callback onErrorWithRestResponse:(RestResponse *)response];
          }
          
          // [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText];
        }];

            
            
            
        ]-*/;
}
