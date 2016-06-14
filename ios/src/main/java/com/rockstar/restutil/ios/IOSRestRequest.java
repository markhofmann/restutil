package com.rockstar.restutil.ios;

import com.google.j2objc.annotations.ObjectiveCName;
import com.rockstar.restutil.common.BaseRestRequest;
import com.rockstar.restutil.common.RestCallback;
import com.rockstar.restutil.common.RestUtil;

/*-[
  #import <NSDictionaryMap.h>
  #import <java/util/HashMap.h>
  #import <java/util/Iterator.h>
  ]-*/


/**
 * @author Mark Hofmann (mark@mark-hofmann.de)
 */
@ObjectiveCName("IOSRestRequest")
public class IOSRestRequest extends BaseRestRequest {
    public IOSRestRequest(RequestMethod requestMethod, String url, RestUtil restUtil) {
        super(requestMethod, url, restUtil);
    }

    @Override
    public native <T, E> void execute(RestCallback<T, E> callback) /*-[
//Translate headers to NSDictionary
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

    // 2
    NSURLSessionConfiguration *sessionConfig =
    [NSURLSessionConfiguration defaultSessionConfiguration];
    [sessionConfig setHTTPAdditionalHeaders:headerDictionary];

    // 3
    NSURLSession *session =
    [NSURLSession sessionWithConfiguration:sessionConfig
                                  delegate:nil
                             delegateQueue:nil];
    switch ([requestMethod_ ordinal])  {
        case RestRequest_RequestMethod_Enum_GET:{

            NSURLSessionDataTask *jsonData = [session dataTaskWithURL:[NSURL URLWithString:url_]
                completionHandler:^(NSData *data,
                                    NSURLResponse *response,
                                    NSError *error) {
                if ([response isKindOfClass:[NSHTTPURLResponse class]]) {

                    if([((NSHTTPURLResponse *)response) statusCode] != 200){
                        NSLog(@"Error getting %@, HTTP status code %li",
                        url_,(long)[((NSHTTPURLResponse *)response)
                        statusCode]);
                        //        return nil;
                    }
                }
                // handle NSData
            }];
            [jsonData resume];
            break;
        }
        case RestRequest_RequestMethod_Enum_POST:{
            // Build the request body
            NSDate *now= [NSDate date] ;
            NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
            [dateFormatter setDateFormat:@"dd-MM-YYYY HH:mm:ss"];
            NSString *boundary =[dateFormatter stringFromDate:now];

            NSMutableData *body = [NSMutableData data];

            // Body part for parameters_ as JSON. This is a string.
            NSError *error;
            // Pass 0 if you don't care about the readability of the generated string
            NSData *jsonData = [NSJSONSerialization dataWithJSONObject:parameterDictionary
                   options:NSJSONWritingPrettyPrinted
                   error:&error];

            if (! jsonData && [parameterDictionary count] == 0) {
                NSLog(@"Got an error: %@", error);
            } else {
                NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];

                [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary]
                dataUsingEncoding:NSUTF8StringEncoding]];
                [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n",
                @"json={"] dataUsingEncoding:NSUTF8StringEncoding]];
                [body appendData:[[NSString stringWithFormat:@"%@\r\n", jsonString]
                dataUsingEncoding:NSUTF8StringEncoding]];
                [body appendData:[[NSString stringWithFormat:@"}"] dataUsingEncoding:NSUTF8StringEncoding]];

            }
            if(fileUri_ != nil){
            // Body part for the attachament. This is an image.
                NSData *fileData = [NSData dataWithContentsOfFile:fileUri_];
                if (fileData) {
                    [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary]
                    dataUsingEncoding:NSUTF8StringEncoding]];
                    [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=%@;filename=image.jpg\r\n", @"image"] dataUsingEncoding:NSUTF8StringEncoding]];
                    [body appendData:[@"Content-Type: image/jpeg\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
                    [body appendData:fileData];
                    [body appendData:[[NSString stringWithFormat:@"\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];
                }
                [body appendData:[[NSString stringWithFormat:@"--%@--\r\n", boundary]
                dataUsingEncoding:NSUTF8StringEncoding]];
            }

            //Build the request
            NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url_]];
            request.HTTPMethod = @"POST";
            request.HTTPBody = body;

            NSURLSessionUploadTask *pData = [session uploadTaskWithRequest:request fromData:body completionHandler:^
            (NSData * _Nullable data, NSURLResponse * _Nullable response, NSError * _Nullable error) {
                if ([response isKindOfClass:[NSHTTPURLResponse class]]) {

                    if([((NSHTTPURLResponse *)response) statusCode] != 200){
                        NSLog(@"Error getting %@, HTTP status code %li", url_,
                        (long)[((NSHTTPURLResponse *)response) statusCode]);
                        //        return nil;
                    }
                }
                // handle NSData
            }];
            [pData resume];
            break;
        }
        default:
            break;
    }

    NSURLSessionDataTask *jsonData = [session dataTaskWithURL:[NSURL URLWithString:url_]
                                            completionHandler:^(NSData *data,
                                                                NSURLResponse *response,
                                                                NSError *error) {
                                                if ([response isKindOfClass:[NSHTTPURLResponse class]]) {

                                                    if([((NSHTTPURLResponse *)response) statusCode] != 200){
                                                        NSLog(@"Error getting %@, HTTP status code %li", url_,
                                                        (long)[((NSHTTPURLResponse *)response) statusCode]);
                                                //        return nil;
                                                    }
                                                }
                                                // handle NSData
                                            }];
    [jsonData resume];
        ]-*/;
}
