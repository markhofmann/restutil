//
//  ViewController.m
//  restutilTest
//
//  Created by Taghva on 12/08/16.
//  Copyright © 2016 sina taghva. All rights reserved.
//

#import "ViewController.h"

#import "IOSRestUtilTest.h"
#import "RestResponse.h"
#import "iOSClass.h"

#import "java/util/Map.h"
#import <java/util/HashMap.h>
#import <java/util/Iterator.h>

#import <UNIRest.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    IOSRestUtilTest *t = [[IOSRestUtilTest alloc] init];
    
//    [t testGet];
    
    jfloat ox = 35.776;
    jfloat oy = 51.464;
    jfloat dx = 35.782776;
    jfloat dy = 51.433414;
    
    [t getGoogleMapsDirectionWithFloat:ox withFloat:oy withFloat:dx withFloat:dy withRestCallback:self];
    
    
//    NSDictionary* headers = @{@"accept": @"application/json"};
//    NSDictionary* parameters = @{@"origin": @"35.776,51.464", @"destination": @"35.782776,51.433414"};
//    
//    UNIHTTPJsonResponse *response = [[UNIRest get:^(UNISimpleRequest *request) {
//        [request setUrl:@"https://maps.googleapis.com/maps/api/directions/json"];
//        [request setHeaders:headers];
//        [request setParameters:parameters];
//    }] asJson];
//    
//    NSLog(@"%@",response.body.JSONObject);
    
//    id<JavaUtilMap> map;
//    
//    JavaUtilHashMap *parameterMap = [[JavaUtilHashMap alloc] initWithJavaUtilMap:map];
//    NSMutableDictionary *parameterDictionary = [[NSMutableDictionary alloc] init];
//    id <JavaUtilIterator> iterator = [parameterMap newKeyIterator];
//    while ([iterator hasNext]) {
//        id object = [iterator next];
//        [parameterDictionary setObject:[parameterMap getWithId:object] forKey:object];
//    }
    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark Rest Callback

-(void)onSuccessWithRestResponse:(RestResponse *)response {
    UNIHTTPJsonResponse *r = (UNIHTTPJsonResponse *)response;
    
    NSLog(@"%@",r.body.JSONObject);
    
    NSDictionary *direction = r.body.JSONObject[@"routes"][0][@"legs"][0];
    
    NSString *distance = direction[@"distance"][@"text"];
    NSString *duration = direction[@"duration"][@"text"];
    
    dispatch_async(dispatch_get_main_queue(), ^{
    
        self.distanceLabel.text = distance;
        self.durationLabel.text = duration;
        
    });
}

-(void)onErrorWithRestResponse:(RestResponse *)errorResponse {
    NSLog(@"error");
}

-(void)onNetworkErrorWithNSString:(NSString *)reason {
    NSLog(@"network error");
}

-(IOSClass *)getResponseType {
    return [IOSClass classForIosName:@"NSDictionary"];
}

-(IOSClass *)getErrorResponseType {
    return [IOSClass classForIosName:@"NSString"];
}

@end
