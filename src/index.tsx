import InAppReviewTurbo from './NativeInAppReviewTurbo';

export function RequestInAppReview(): Promise<boolean> {
  return InAppReviewTurbo.request();
}

export function IsAvailable(): Promise<boolean> {
  return InAppReviewTurbo.isAvailable();
}
