package cn.com.isurpass.securityplatform.alarm;

import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.message.vo.Event;

public interface IAlarmMessageSender 
{
	/**
	 *  如果 alarmvalue 字段存在值,优先使用alarmvaue中的值,而不是通过msg去查找.
	 * @param msg
	 * @param groupid
	 * @param usercode
	 * @param zone
	 * @param alarmvalue
	 * @return
	 */
	boolean sendAlarmMessage(Event event , UserPO user, int zone, String alarmvalue);
}
