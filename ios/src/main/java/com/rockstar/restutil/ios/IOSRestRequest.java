package com.rockstar.restutil.ios;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestUtil;

/*-[
  #import <NSDictionaryMap.h>
  #import "java/util/HashMap.h"
  #import "java/util/Iterator.h"
  #import <AFNetworking/AFNetworking.h>
  ]-*/


/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestRequest")
public class IOSRestRequest extends BaseRestRequest {
    public IOSRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        super(requestMethod, url, restUtil);
    }


    /*-[ - (void)makeResponse:(NSURLSessionTask*)task withResponse:(id)response callback:(id<RestCallback>) callback {

          NSHTTPURLResponse* r = (NSHTTPURLResponse*)task.response;

          NSInteger code = r.statusCode;
          NSDictionary *responseHeaders = r.allHeaderFields;

          NSError *err;
          NSData *jsonData = [NSJSONSerialization dataWithJSONObject:response options:0 error:&err];
          NSString *bodyString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

          NSString *responseText = bodyString;

          id<JavaUtilMap> headersMap = create_JavaUtilHashMap_init();
          for (NSString *key in responseHeaders.allKeys) {
            [headersMap putWithId:key withId:responseHeaders[key]];
          }

          [self executeCallbackWithRestCallback:callback withInt:code withNSString:responseText
          withJavaUtilMap:headersMap];

        }

    ]-*/;

    /**
     * Native implementation of the execute method.
     * See {@link com.rockstar.restutil.common.RestRequest#execute(RestCallback)} for details.
     */
    @Override
    public native <T, E> void execute(RestCallback<T, E> callback) /*-[

      //Translate headers_ to NSDictionary
      JavaUtilHashMap *headerMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:headers_];
      NSMutableDictionary *headerDictionary = [[NSMutableDictionary alloc] init];
      id <JavaUtilIterator> iterator = [headerMap newKeyIterator];
      while ([iterator hasNext]) {
        id object = [iterator next];
        [headerDictionary setObject:[headerMap getWithId:object] forKey:object];
      }

      //Translate parameters_ to NSDictionary
      JavaUtilHashMap *parameterMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:parameters_];
      NSMutableDictionary *parameterRequest = [[NSMutableDictionary alloc] init];
      iterator = [parameterMap newKeyIterator];
      while ([iterator hasNext]) {
        id object = [iterator next];
        [parameterRequest setObject:[parameterMap getWithId:object] forKey:object];
      }

      if ([self getFileUri]) {
        NSURL *url = [NSURL fileURLWithPath:[self getFileUri]];
        [parameterRequest setObject:url forKey:@"fileUpload"];
      }

      AFHTTPSessionManager *manager = [[AFHTTPSessionManager alloc] init];

      manager.requestSerializer = [AFJSONRequestSerializer serializer];

      for (NSString *headerKey in headerDictionary) {
          [manager.requestSerializer setValue:[headerDictionary objectForKey:headerKey]
                             forHTTPHeaderField:headerKey];
      }



      if(body_ != nil || ![body_ isEqual:[NSNull null]] || body_.length != 0 || ![body_ isEqualToString:@"null"]) {

          NSString *jsonString = body_;
          NSData *data = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
          id json = [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];

          if ([json isKindOfClass:[NSArray class]]) {

              parameterRequest = json;

          } else {
              for (NSString *parametersKey in json) {
                  [parameterRequest setObject:[json objectForKey:parametersKey] forKey:parametersKey];
              }
          }


      }

        // This might be overkill
        // NSLog(@"NO BODY");

        if ([[[self getRequestMethod] getMethod] isEqualToString:@"GET"]) {

            [manager GET:[self getUrl] parameters:parameterRequest progress:nil success:^(NSURLSessionTask
                                                                                             *task, id
                                                                                             responseObject) {

                [self makeResponse:task withResponse:responseObject callback:callback];

            } failure:^(NSURLSessionTask *operation, NSError *error) {

                NSLog(@"Error GET %@", error);

            }];


        } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"POST"]) {

            [manager POST:[self getUrl] parameters:parameterRequest progress:^(NSProgress * _Nonnull
                                                                                  uploadProgress) {

            } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {

                [self makeResponse:task withResponse:responseObject callback:callback];

            } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {

                NSLog(@"Error POST %@", error);

            }];

        } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"PUT"]) {

            [manager PUT:[self getUrl] parameters:parameterRequest success:^(NSURLSessionDataTask * _Nonnull
                                                                                task, id
                                                                                _Nullable responseObject) {

                [self makeResponse:task withResponse:responseObject callback:callback];

            } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {

                NSLog(@"Error PUT %@", error);

            }];

        } else if ([[[self getRequestMethod] getMethod] isEqualToString:@"DELETE"]) {

            [manager DELETE:[self getUrl] parameters:parameterRequest success:^(NSURLSessionDataTask * _Nonnull
                                                                                   task, id
                                                                                   _Nullable responseObject) {

                [self makeResponse:task withResponse:responseObject callback:callback];

            } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {

                NSLog(@"Error DELETE %@", error);

            }];

        }
      }
    ]-*/;
}
