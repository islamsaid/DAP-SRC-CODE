import { ProfileModel } from './profile.model';

export class User {
  userId?: number;
  name?: string;
  username?: string;
  profileId?: number;
  lockFlag?: any;
  userProfileModel?: ProfileModel;
}
