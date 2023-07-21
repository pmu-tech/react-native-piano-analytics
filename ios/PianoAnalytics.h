
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNPianoAnalyticsSpec.h"

@interface RNPianoAnalytics : NSObject <NativePianoAnalyticsSpec>
#else
#import <React/RCTBridgeModule.h>

@interface RNPianoAnalytics : NSObject <RCTBridgeModule>
#endif

@end
