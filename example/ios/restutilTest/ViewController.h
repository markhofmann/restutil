//
//  ViewController.h
//  restutilTest
//
//  Created by Taghva on 12/08/16.
//  Copyright © 2016 sina taghva. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "RestCallback.h"

@interface ViewController : UIViewController<RestCallback>

@property (strong, nonatomic) IBOutlet UILabel *distanceLabel;
@property (strong, nonatomic) IBOutlet UILabel *durationLabel;

@end

