#ifdef RCT_NEW_ARCH_ENABLED
#import "RNPianoAnalyticsSpec.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#ifdef RCT_NEW_ARCH_ENABLED
@interface RNPianoAnalytics : NSObject <NativePianoAnalyticsSpec>
#else
@interface RNPianoAnalytics : NSObject <RCTBridgeModule>
#endif

@end
