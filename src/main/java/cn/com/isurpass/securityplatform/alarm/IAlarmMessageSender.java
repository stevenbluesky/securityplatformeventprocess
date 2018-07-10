package cn.com.isurpass.securityplatform.alarm;

public interface IAlarmMessageSender 
{
	boolean sendAlarmMessage(String msg , String groupid , String usercode, int zone);

	/**
	 *  如果 alarmvalue 字段存在值,优先使用alarmvaue中的值,而不是通过msg去查找.
	 * @param msg
	 * @param groupid
	 * @param usercode
	 * @param zone
	 * @param alarmvalue
	 * @return
	 */
	boolean sendAlarmMessage(String msg , String groupid , String usercode, int zone, String alarmvalue);
}
