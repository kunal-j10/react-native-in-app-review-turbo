#import "InAppReviewTurbo.h"
#import <StoreKit/StoreKit.h>
#import <UIKit/UIKit.h>

@implementation InAppReviewTurbo

- (BOOL)_isAvailableSync
{
  if (@available(iOS 10.3, *)) {
    return YES;
  }
  return NO;
}

- (void)isAvailable:(RCTPromiseResolveBlock)resolve
      reject:(RCTPromiseRejectBlock)reject
{
  @try {
    resolve(@([self _isAvailableSync]));
  }
  @catch (NSException *exception) {
    reject(@"ERR_INAPP_REVIEW_AVAILABLE", exception.reason, nil);
  }
}

- (void)request:(RCTPromiseResolveBlock)resolve
        reject:(RCTPromiseRejectBlock)reject
{
  @try {
    if (![self _isAvailableSync]) {
      resolve(@(NO));
      return;
    }

    dispatch_async(dispatch_get_main_queue(), ^{

      if (@available(iOS 14.0, *)) {
        UIWindowScene *activeScene = nil;

        for (UIScene *scene in UIApplication.sharedApplication.connectedScenes) {
          if (scene.activationState == UISceneActivationStateForegroundActive &&
            [scene isKindOfClass:[UIWindowScene class]]) {
                activeScene = (UIWindowScene *)scene;
                break;
          }
        }

        if (activeScene != nil) {
          [SKStoreReviewController requestReviewInScene:activeScene];
        } else {
          [SKStoreReviewController requestReview];
        }
      } else {
        [SKStoreReviewController requestReview];
      }

      resolve(@(YES));
    });
  }
  @catch (NSException *exception) {
    reject(@"ERR_INAPP_REVIEW", exception.reason, nil);
  }
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeInAppReviewTurboSpecJSI>(params);
}

+ (NSString *)moduleName
{
  return @"InAppReviewTurbo";
}

@end
