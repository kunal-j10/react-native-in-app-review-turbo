import { TurboModuleRegistry, type TurboModule } from 'react-native';

export interface Spec extends TurboModule {
  request(): Promise<boolean>;
  isAvailable(): Promise<boolean>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('InAppReviewTurbo');
