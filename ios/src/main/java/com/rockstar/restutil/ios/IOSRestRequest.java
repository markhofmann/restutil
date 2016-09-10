package com.rockstar.restutil.ios;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.RestUtil;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;

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
    public IOSRestRequest(RequestMethod requestMethod, String urll, RestUtil restUtil) {
        super(requestMethod, urll, restUtil);
    }

    /*-[ - (void)makeRequest:(UNISimpleRequest *)request params:(NSDictionary *)params headers:(NSDictionary *)headers{

          [request setUrl:[self getUrl]];
          [request setHeaders:headers];
          [request setParameters:params];
        }
    ]-*/;

    /*-[ - (void)makeResponse:(UNIHTTPStringResponse*) response callback:(id<RestCallback>) callback {

          NSInteger code = response.code;
          NSDictionary *responseHeaders = response.headers;
          
          NSString *responseText = response.body;

          id<JavaUtilMap> headersMap = create_JavaUtilHashMap_init();
          for (NSString *key in responseHeaders.allKeys) {
              [headersMap putWithId:key withId:responseHeaders[key]];
          }

          [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText withJavaUtilMap:headersMap];
        }
    ]-*/;

    /**
     * Native implementation of the execute method.
     * See {@link com.rockstar.restutil.common.RestRequest#execute(RestCallback)} for details.
     */
    @Override
    public native <T, E> void execute(RestCallback<T, E> callback) /*-[
      // NSDictionary* headers = @{@"accept": @"application/json"};
      //NSDictionary* parameters = @{@"origin": @"35.776,51.464", @"destination": @"35.782776,51.433414"};
      
      // UNIHTTPJsonResponse *response = [[UNIRest get:^(UNISimpleRequest *request) {
      //     [request setUrl:@"https://maps.googleapis.com/maps/api/directions/json"];
      //     [request setHeaders:headers];
      //     [request setParameters:parameters];
      // }] asJson];

      // Translate headers to NSDictionary
      JavaUtilHashMap *headerMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:headers_];
      NSMutableDictionary *headerDictionary = [[NSMutableDictionary alloc] init];
      id <JavaUtilIterator> iterator = [headerMap newKeyIterator];
      while ([iterator hasNext]) {
          id object = [iterator next];
          [headerDictionary setObject:[headerMap getWithId:object] forKey:object];
      }

      //Translate parameters_ to NSDictionary
      JavaUtilHashMap *parameterMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:parameters_];
      NSMutableDictionary *parameterDictionary = [[NSMutableDictionary alloc] init];
      iterator = [parameterMap newKeyIterator];
      while ([iterator hasNext]) {
          id object = [iterator next];
          [parameterDictionary setObject:[parameterMap getWithId:object] forKey:object];
      }

      if ([self getFileUri]) {
          NSURL *url = [NSURL fileURLWithPath:[self getFileUri]];
          [parameterDictionary setObject:url forKey:@"fileUpload"];
      }

      // NSLog(@"params: %@",parameterDictionary);
      // NSLog(@"headers: %@",headerDictionary);

      if ([[[self getRequestMethod] getMethod] isEqualToString:@"GET"]) {
        [[UNIRest get:^(UNISimpleRequest *request) {
          [self makeRequest:request params:parameterDictionary headers:headerDictionary];
        }] asStringAsync:^(UNIHTTPStringResponse* response, NSError *error) {
          [self makeResponse:response callback:callback];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"POST"]) {
        [[UNIRest post:^(UNISimpleRequest *request) {
          [self makeRequest:request params:parameterDictionary headers:headerDictionary];
        }] asStringAsync:^(UNIHTTPStringResponse* response, NSError *error) {
          [self makeResponse:response callback:callback];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"PUT"]) {
        [[UNIRest put:^(UNISimpleRequest *request) {
          [self makeRequest:request params:parameterDictionary headers:headerDictionary];
        }] asStringAsync:^(UNIHTTPStringResponse* response, NSError *error) {
          [self makeResponse:response callback:callback];
        }];
      } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"DELETE"]) {
        [[UNIRest delete:^(UNISimpleRequest *request) {
          [self makeRequest:request params:parameterDictionary headers:headerDictionary];
        }] asStringAsync:^(UNIHTTPStringResponse* response, NSError *error) {
          [self makeResponse:response callback:callback];
        }];

      }  
            
            
        ]-*/;
}
