/*
 * 2017/10/05 DX Creating
 *
 * (c) Copyright TIS Inc. All rights reserved.
 */
package vo
{
    /**
     * <p> Domain Class</p>
     *
     * @author DX
     * @version 1.00
     */
    [RemoteClass(alias="com.base.db.dto.TaccountsDto")]
    [Bindable]
    public class TaccountsVo {
    
        /**  */
        private var _account:String;
    
        /**  */
        private var _password:String;
    
        /**
         * Default Constants
         */
        public function TaccountsVo():void {
    
        }
    
        /**
         * account getter
         *
         * @return the account
         */
        public function get account():String{
            return _account;
        }
    
        /**
         * account setter
         *
         * @param account account 
         */
        public function set account(account:String):void {
            this._account = account;
        }
    
        /**
         * password getter
         *
         * @return the password
         */
        public function get password():String{
            return _password;
        }
    
        /**
         * password setter
         *
         * @param password password 
         */
        public function set password(password:String):void {
            this._password = password;
        }
    
    }
}
