// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package projet11.loading_dim_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: Loading_dim Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Loading_dim implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "Loading_dim";
	private final String projectName = "PROJET11";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					Loading_dim.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(Loading_dim.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class dim_outletStruct implements routines.system.IPersistableRow<dim_outletStruct> {
		final static byte[] commonByteArrayLock_PROJET11_Loading_dim = new byte[0];
		static byte[] commonByteArray_PROJET11_Loading_dim = new byte[0];

		public Integer outlet_key;

		public Integer getOutlet_key() {
			return this.outlet_key;
		}

		public String Nom;

		public String getNom() {
			return this.Nom;
		}

		public String Type;

		public String getType() {
			return this.Type;
		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public String Province;

		public String getProvince() {
			return this.Province;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.outlet_key = readInteger(dis);

					this.Nom = readString(dis);

					this.Type = readString(dis);

					this.City = readString(dis);

					this.Province = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.outlet_key = readInteger(dis);

					this.Nom = readString(dis);

					this.Type = readString(dis);

					this.City = readString(dis);

					this.Province = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.outlet_key, dos);

				// String

				writeString(this.Nom, dos);

				// String

				writeString(this.Type, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.Province, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.outlet_key, dos);

				// String

				writeString(this.Nom, dos);

				// String

				writeString(this.Type, dos);

				// String

				writeString(this.City, dos);

				// String

				writeString(this.Province, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("outlet_key=" + String.valueOf(outlet_key));
			sb.append(",Nom=" + Nom);
			sb.append(",Type=" + Type);
			sb.append(",City=" + City);
			sb.append(",Province=" + Province);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(dim_outletStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_PROJET11_Loading_dim = new byte[0];
		static byte[] commonByteArray_PROJET11_Loading_dim = new byte[0];

		public String INSURER_NM;

		public String getINSURER_NM() {
			return this.INSURER_NM;
		}

		public BigDecimal CLAIM_ID;

		public BigDecimal getCLAIM_ID() {
			return this.CLAIM_ID;
		}

		public java.util.Date CLAIM_CREATE_DT;

		public java.util.Date getCLAIM_CREATE_DT() {
			return this.CLAIM_CREATE_DT;
		}

		public String CURRENT_STATUS;

		public String getCURRENT_STATUS() {
			return this.CURRENT_STATUS;
		}

		public String CURRENT_SUBSTATUS;

		public String getCURRENT_SUBSTATUS() {
			return this.CURRENT_SUBSTATUS;
		}

		public java.util.Date INVOICE_DATE;

		public java.util.Date getINVOICE_DATE() {
			return this.INVOICE_DATE;
		}

		public String OUTLET_NM;

		public String getOUTLET_NM() {
			return this.OUTLET_NM;
		}

		public String OUTLET_TYPE;

		public String getOUTLET_TYPE() {
			return this.OUTLET_TYPE;
		}

		public String OUTLET_CITY;

		public String getOUTLET_CITY() {
			return this.OUTLET_CITY;
		}

		public String OUTLET_PROVINCE;

		public String getOUTLET_PROVINCE() {
			return this.OUTLET_PROVINCE;
		}

		public BigDecimal CLAIM_VEHICLE_YEAR;

		public BigDecimal getCLAIM_VEHICLE_YEAR() {
			return this.CLAIM_VEHICLE_YEAR;
		}

		public String CLAIM_VEHICLE_MAKE;

		public String getCLAIM_VEHICLE_MAKE() {
			return this.CLAIM_VEHICLE_MAKE;
		}

		public String CLAIM_VEHICLE_MODEL;

		public String getCLAIM_VEHICLE_MODEL() {
			return this.CLAIM_VEHICLE_MODEL;
		}

		public String EVENT_STATUS;

		public String getEVENT_STATUS() {
			return this.EVENT_STATUS;
		}

		public String EVENT_SUBSTATUS;

		public String getEVENT_SUBSTATUS() {
			return this.EVENT_SUBSTATUS;
		}

		public java.util.Date FIRST_TIME_IN_STATUS;

		public java.util.Date getFIRST_TIME_IN_STATUS() {
			return this.FIRST_TIME_IN_STATUS;
		}

		public Float TOTAL_HRS_IN_STATUS;

		public Float getTOTAL_HRS_IN_STATUS() {
			return this.TOTAL_HRS_IN_STATUS;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.INSURER_NM = readString(dis);

					this.CLAIM_ID = (BigDecimal) dis.readObject();

					this.CLAIM_CREATE_DT = readDate(dis);

					this.CURRENT_STATUS = readString(dis);

					this.CURRENT_SUBSTATUS = readString(dis);

					this.INVOICE_DATE = readDate(dis);

					this.OUTLET_NM = readString(dis);

					this.OUTLET_TYPE = readString(dis);

					this.OUTLET_CITY = readString(dis);

					this.OUTLET_PROVINCE = readString(dis);

					this.CLAIM_VEHICLE_YEAR = (BigDecimal) dis.readObject();

					this.CLAIM_VEHICLE_MAKE = readString(dis);

					this.CLAIM_VEHICLE_MODEL = readString(dis);

					this.EVENT_STATUS = readString(dis);

					this.EVENT_SUBSTATUS = readString(dis);

					this.FIRST_TIME_IN_STATUS = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.TOTAL_HRS_IN_STATUS = null;
					} else {
						this.TOTAL_HRS_IN_STATUS = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.INSURER_NM = readString(dis);

					this.CLAIM_ID = (BigDecimal) dis.readObject();

					this.CLAIM_CREATE_DT = readDate(dis);

					this.CURRENT_STATUS = readString(dis);

					this.CURRENT_SUBSTATUS = readString(dis);

					this.INVOICE_DATE = readDate(dis);

					this.OUTLET_NM = readString(dis);

					this.OUTLET_TYPE = readString(dis);

					this.OUTLET_CITY = readString(dis);

					this.OUTLET_PROVINCE = readString(dis);

					this.CLAIM_VEHICLE_YEAR = (BigDecimal) dis.readObject();

					this.CLAIM_VEHICLE_MAKE = readString(dis);

					this.CLAIM_VEHICLE_MODEL = readString(dis);

					this.EVENT_STATUS = readString(dis);

					this.EVENT_SUBSTATUS = readString(dis);

					this.FIRST_TIME_IN_STATUS = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.TOTAL_HRS_IN_STATUS = null;
					} else {
						this.TOTAL_HRS_IN_STATUS = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.INSURER_NM, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_ID);

				// java.util.Date

				writeDate(this.CLAIM_CREATE_DT, dos);

				// String

				writeString(this.CURRENT_STATUS, dos);

				// String

				writeString(this.CURRENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.INVOICE_DATE, dos);

				// String

				writeString(this.OUTLET_NM, dos);

				// String

				writeString(this.OUTLET_TYPE, dos);

				// String

				writeString(this.OUTLET_CITY, dos);

				// String

				writeString(this.OUTLET_PROVINCE, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_VEHICLE_YEAR);

				// String

				writeString(this.CLAIM_VEHICLE_MAKE, dos);

				// String

				writeString(this.CLAIM_VEHICLE_MODEL, dos);

				// String

				writeString(this.EVENT_STATUS, dos);

				// String

				writeString(this.EVENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.FIRST_TIME_IN_STATUS, dos);

				// Float

				if (this.TOTAL_HRS_IN_STATUS == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.TOTAL_HRS_IN_STATUS);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.INSURER_NM, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_ID);

				// java.util.Date

				writeDate(this.CLAIM_CREATE_DT, dos);

				// String

				writeString(this.CURRENT_STATUS, dos);

				// String

				writeString(this.CURRENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.INVOICE_DATE, dos);

				// String

				writeString(this.OUTLET_NM, dos);

				// String

				writeString(this.OUTLET_TYPE, dos);

				// String

				writeString(this.OUTLET_CITY, dos);

				// String

				writeString(this.OUTLET_PROVINCE, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_VEHICLE_YEAR);

				// String

				writeString(this.CLAIM_VEHICLE_MAKE, dos);

				// String

				writeString(this.CLAIM_VEHICLE_MODEL, dos);

				// String

				writeString(this.EVENT_STATUS, dos);

				// String

				writeString(this.EVENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.FIRST_TIME_IN_STATUS, dos);

				// Float

				if (this.TOTAL_HRS_IN_STATUS == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.TOTAL_HRS_IN_STATUS);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("INSURER_NM=" + INSURER_NM);
			sb.append(",CLAIM_ID=" + String.valueOf(CLAIM_ID));
			sb.append(",CLAIM_CREATE_DT=" + String.valueOf(CLAIM_CREATE_DT));
			sb.append(",CURRENT_STATUS=" + CURRENT_STATUS);
			sb.append(",CURRENT_SUBSTATUS=" + CURRENT_SUBSTATUS);
			sb.append(",INVOICE_DATE=" + String.valueOf(INVOICE_DATE));
			sb.append(",OUTLET_NM=" + OUTLET_NM);
			sb.append(",OUTLET_TYPE=" + OUTLET_TYPE);
			sb.append(",OUTLET_CITY=" + OUTLET_CITY);
			sb.append(",OUTLET_PROVINCE=" + OUTLET_PROVINCE);
			sb.append(",CLAIM_VEHICLE_YEAR=" + String.valueOf(CLAIM_VEHICLE_YEAR));
			sb.append(",CLAIM_VEHICLE_MAKE=" + CLAIM_VEHICLE_MAKE);
			sb.append(",CLAIM_VEHICLE_MODEL=" + CLAIM_VEHICLE_MODEL);
			sb.append(",EVENT_STATUS=" + EVENT_STATUS);
			sb.append(",EVENT_SUBSTATUS=" + EVENT_SUBSTATUS);
			sb.append(",FIRST_TIME_IN_STATUS=" + String.valueOf(FIRST_TIME_IN_STATUS));
			sb.append(",TOTAL_HRS_IN_STATUS=" + String.valueOf(TOTAL_HRS_IN_STATUS));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_PROJET11_Loading_dim = new byte[0];
		static byte[] commonByteArray_PROJET11_Loading_dim = new byte[0];

		public String INSURER_NM;

		public String getINSURER_NM() {
			return this.INSURER_NM;
		}

		public BigDecimal CLAIM_ID;

		public BigDecimal getCLAIM_ID() {
			return this.CLAIM_ID;
		}

		public java.util.Date CLAIM_CREATE_DT;

		public java.util.Date getCLAIM_CREATE_DT() {
			return this.CLAIM_CREATE_DT;
		}

		public String CURRENT_STATUS;

		public String getCURRENT_STATUS() {
			return this.CURRENT_STATUS;
		}

		public String CURRENT_SUBSTATUS;

		public String getCURRENT_SUBSTATUS() {
			return this.CURRENT_SUBSTATUS;
		}

		public java.util.Date INVOICE_DATE;

		public java.util.Date getINVOICE_DATE() {
			return this.INVOICE_DATE;
		}

		public String OUTLET_NM;

		public String getOUTLET_NM() {
			return this.OUTLET_NM;
		}

		public String OUTLET_TYPE;

		public String getOUTLET_TYPE() {
			return this.OUTLET_TYPE;
		}

		public String OUTLET_CITY;

		public String getOUTLET_CITY() {
			return this.OUTLET_CITY;
		}

		public String OUTLET_PROVINCE;

		public String getOUTLET_PROVINCE() {
			return this.OUTLET_PROVINCE;
		}

		public BigDecimal CLAIM_VEHICLE_YEAR;

		public BigDecimal getCLAIM_VEHICLE_YEAR() {
			return this.CLAIM_VEHICLE_YEAR;
		}

		public String CLAIM_VEHICLE_MAKE;

		public String getCLAIM_VEHICLE_MAKE() {
			return this.CLAIM_VEHICLE_MAKE;
		}

		public String CLAIM_VEHICLE_MODEL;

		public String getCLAIM_VEHICLE_MODEL() {
			return this.CLAIM_VEHICLE_MODEL;
		}

		public String EVENT_STATUS;

		public String getEVENT_STATUS() {
			return this.EVENT_STATUS;
		}

		public String EVENT_SUBSTATUS;

		public String getEVENT_SUBSTATUS() {
			return this.EVENT_SUBSTATUS;
		}

		public java.util.Date FIRST_TIME_IN_STATUS;

		public java.util.Date getFIRST_TIME_IN_STATUS() {
			return this.FIRST_TIME_IN_STATUS;
		}

		public Float TOTAL_HRS_IN_STATUS;

		public Float getTOTAL_HRS_IN_STATUS() {
			return this.TOTAL_HRS_IN_STATUS;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJET11_Loading_dim.length) {
					if (length < 1024 && commonByteArray_PROJET11_Loading_dim.length == 0) {
						commonByteArray_PROJET11_Loading_dim = new byte[1024];
					} else {
						commonByteArray_PROJET11_Loading_dim = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJET11_Loading_dim, 0, length);
				strReturn = new String(commonByteArray_PROJET11_Loading_dim, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.INSURER_NM = readString(dis);

					this.CLAIM_ID = (BigDecimal) dis.readObject();

					this.CLAIM_CREATE_DT = readDate(dis);

					this.CURRENT_STATUS = readString(dis);

					this.CURRENT_SUBSTATUS = readString(dis);

					this.INVOICE_DATE = readDate(dis);

					this.OUTLET_NM = readString(dis);

					this.OUTLET_TYPE = readString(dis);

					this.OUTLET_CITY = readString(dis);

					this.OUTLET_PROVINCE = readString(dis);

					this.CLAIM_VEHICLE_YEAR = (BigDecimal) dis.readObject();

					this.CLAIM_VEHICLE_MAKE = readString(dis);

					this.CLAIM_VEHICLE_MODEL = readString(dis);

					this.EVENT_STATUS = readString(dis);

					this.EVENT_SUBSTATUS = readString(dis);

					this.FIRST_TIME_IN_STATUS = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.TOTAL_HRS_IN_STATUS = null;
					} else {
						this.TOTAL_HRS_IN_STATUS = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJET11_Loading_dim) {

				try {

					int length = 0;

					this.INSURER_NM = readString(dis);

					this.CLAIM_ID = (BigDecimal) dis.readObject();

					this.CLAIM_CREATE_DT = readDate(dis);

					this.CURRENT_STATUS = readString(dis);

					this.CURRENT_SUBSTATUS = readString(dis);

					this.INVOICE_DATE = readDate(dis);

					this.OUTLET_NM = readString(dis);

					this.OUTLET_TYPE = readString(dis);

					this.OUTLET_CITY = readString(dis);

					this.OUTLET_PROVINCE = readString(dis);

					this.CLAIM_VEHICLE_YEAR = (BigDecimal) dis.readObject();

					this.CLAIM_VEHICLE_MAKE = readString(dis);

					this.CLAIM_VEHICLE_MODEL = readString(dis);

					this.EVENT_STATUS = readString(dis);

					this.EVENT_SUBSTATUS = readString(dis);

					this.FIRST_TIME_IN_STATUS = readDate(dis);

					length = dis.readByte();
					if (length == -1) {
						this.TOTAL_HRS_IN_STATUS = null;
					} else {
						this.TOTAL_HRS_IN_STATUS = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.INSURER_NM, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_ID);

				// java.util.Date

				writeDate(this.CLAIM_CREATE_DT, dos);

				// String

				writeString(this.CURRENT_STATUS, dos);

				// String

				writeString(this.CURRENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.INVOICE_DATE, dos);

				// String

				writeString(this.OUTLET_NM, dos);

				// String

				writeString(this.OUTLET_TYPE, dos);

				// String

				writeString(this.OUTLET_CITY, dos);

				// String

				writeString(this.OUTLET_PROVINCE, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_VEHICLE_YEAR);

				// String

				writeString(this.CLAIM_VEHICLE_MAKE, dos);

				// String

				writeString(this.CLAIM_VEHICLE_MODEL, dos);

				// String

				writeString(this.EVENT_STATUS, dos);

				// String

				writeString(this.EVENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.FIRST_TIME_IN_STATUS, dos);

				// Float

				if (this.TOTAL_HRS_IN_STATUS == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.TOTAL_HRS_IN_STATUS);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.INSURER_NM, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_ID);

				// java.util.Date

				writeDate(this.CLAIM_CREATE_DT, dos);

				// String

				writeString(this.CURRENT_STATUS, dos);

				// String

				writeString(this.CURRENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.INVOICE_DATE, dos);

				// String

				writeString(this.OUTLET_NM, dos);

				// String

				writeString(this.OUTLET_TYPE, dos);

				// String

				writeString(this.OUTLET_CITY, dos);

				// String

				writeString(this.OUTLET_PROVINCE, dos);

				// BigDecimal

				dos.writeObject(this.CLAIM_VEHICLE_YEAR);

				// String

				writeString(this.CLAIM_VEHICLE_MAKE, dos);

				// String

				writeString(this.CLAIM_VEHICLE_MODEL, dos);

				// String

				writeString(this.EVENT_STATUS, dos);

				// String

				writeString(this.EVENT_SUBSTATUS, dos);

				// java.util.Date

				writeDate(this.FIRST_TIME_IN_STATUS, dos);

				// Float

				if (this.TOTAL_HRS_IN_STATUS == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.TOTAL_HRS_IN_STATUS);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("INSURER_NM=" + INSURER_NM);
			sb.append(",CLAIM_ID=" + String.valueOf(CLAIM_ID));
			sb.append(",CLAIM_CREATE_DT=" + String.valueOf(CLAIM_CREATE_DT));
			sb.append(",CURRENT_STATUS=" + CURRENT_STATUS);
			sb.append(",CURRENT_SUBSTATUS=" + CURRENT_SUBSTATUS);
			sb.append(",INVOICE_DATE=" + String.valueOf(INVOICE_DATE));
			sb.append(",OUTLET_NM=" + OUTLET_NM);
			sb.append(",OUTLET_TYPE=" + OUTLET_TYPE);
			sb.append(",OUTLET_CITY=" + OUTLET_CITY);
			sb.append(",OUTLET_PROVINCE=" + OUTLET_PROVINCE);
			sb.append(",CLAIM_VEHICLE_YEAR=" + String.valueOf(CLAIM_VEHICLE_YEAR));
			sb.append(",CLAIM_VEHICLE_MAKE=" + CLAIM_VEHICLE_MAKE);
			sb.append(",CLAIM_VEHICLE_MODEL=" + CLAIM_VEHICLE_MODEL);
			sb.append(",EVENT_STATUS=" + EVENT_STATUS);
			sb.append(",EVENT_SUBSTATUS=" + EVENT_SUBSTATUS);
			sb.append(",FIRST_TIME_IN_STATUS=" + String.valueOf(FIRST_TIME_IN_STATUS));
			sb.append(",TOTAL_HRS_IN_STATUS=" + String.valueOf(TOTAL_HRS_IN_STATUS));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				dim_outletStruct dim_outlet = new dim_outletStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "dim_outlet");
				}

				int tos_count_tDBOutput_1 = 0;

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int tmp_batchUpdateCount_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				boolean whetherReject_tDBOutput_1 = false;

				java.sql.Connection conn_tDBOutput_1 = null;

				// optional table
				String dbschema_tDBOutput_1 = null;
				String tableName_tDBOutput_1 = null;
				String driverClass_tDBOutput_1 = "oracle.jdbc.OracleDriver";

				java.lang.Class.forName(driverClass_tDBOutput_1);
				String url_tDBOutput_1 = null;
				url_tDBOutput_1 = "jdbc:oracle:thin:@" + "localhost" + ":" + "1521" + ":" + "xe";
				String dbUser_tDBOutput_1 = "system";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:jSDFvphtASJ8q9zR8e+reCm06Ugecyhh2JCJQ8ao");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;
				dbschema_tDBOutput_1 = "";

				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);
				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;
				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;
				int count_tDBOutput_1 = 0;

				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("dim_outlet");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "." + ("dim_outlet");
				}
				String tableNameForSearch_tDBOutput_1 = "" + ((String) "dim_outlet") + "";
				String dbschemaForSearch_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					dbschemaForSearch_tDBOutput_1 = ((String) "system").toUpperCase();
				} else {
					dbschemaForSearch_tDBOutput_1 = dbschema_tDBOutput_1.toUpperCase();
				}

				java.sql.DatabaseMetaData dbMetaData_tDBOutput_1 = conn_tDBOutput_1.getMetaData();
				if (tableNameForSearch_tDBOutput_1.indexOf("\"") == -1) {
					tableNameForSearch_tDBOutput_1 = tableNameForSearch_tDBOutput_1.toUpperCase();
				} else {
					tableNameForSearch_tDBOutput_1 = tableNameForSearch_tDBOutput_1.replaceAll("\"", "");
				}
				boolean whetherExist_tDBOutput_1 = false;
				try (java.sql.ResultSet rsTable_tDBOutput_1 = dbMetaData_tDBOutput_1.getTables(null,
						dbschemaForSearch_tDBOutput_1, tableNameForSearch_tDBOutput_1, new String[] { "TABLE" })) {
					if (rsTable_tDBOutput_1.next()) {
						whetherExist_tDBOutput_1 = true;
					}
				}

				if (whetherExist_tDBOutput_1) {
					try (java.sql.Statement stmtDrop_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
						stmtDrop_tDBOutput_1.execute("DROP TABLE " + tableName_tDBOutput_1 + "");
					}
				}
				try (java.sql.Statement stmtCreate_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
					stmtCreate_tDBOutput_1.execute("CREATE TABLE " + tableName_tDBOutput_1
							+ "(outlet_key INT ,Nom VARCHAR2(50)  ,Type VARCHAR2(50)  ,City VARCHAR2(50)  ,Province VARCHAR2(50)  )");
				}
				String insert_tDBOutput_1 = "INSERT INTO " + tableName_tDBOutput_1
						+ " (outlet_key,Nom,Type,City,Province) VALUES (?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				dim_outletStruct dim_outlet_tmp = new dim_outletStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tUniqRow_1 = 0;

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String OUTLET_NM;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.OUTLET_NM == null) ? 0 : this.OUTLET_NM.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.OUTLET_NM == null) {
							if (other.OUTLET_NM != null)
								return false;

						} else if (!this.OUTLET_NM.equals(other.OUTLET_NM))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "oracle.jdbc.OracleDriver";
				java.lang.Class.forName(driverClass_tDBInput_1);

				String url_tDBInput_1 = null;
				url_tDBInput_1 = "jdbc:oracle:thin:@" + "localhost" + ":" + "1521" + ":" + "xe";

				String dbUser_tDBInput_1 = "system";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:IK2VllJcvo7LVkr+yIqwl3w18io24SokdTb1yHSx");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);
				java.sql.Statement stmtGetTZ_tDBInput_1 = conn_tDBInput_1.createStatement();
				java.sql.ResultSet rsGetTZ_tDBInput_1 = stmtGetTZ_tDBInput_1
						.executeQuery("select sessiontimezone from dual");
				String sessionTimezone_tDBInput_1 = java.util.TimeZone.getDefault().getID();
				while (rsGetTZ_tDBInput_1.next()) {
					sessionTimezone_tDBInput_1 = rsGetTZ_tDBInput_1.getString(1);
				}
				if (!(conn_tDBInput_1 instanceof oracle.jdbc.OracleConnection)
						&& conn_tDBInput_1.isWrapperFor(oracle.jdbc.OracleConnection.class)) {
					if (conn_tDBInput_1.unwrap(oracle.jdbc.OracleConnection.class) != null) {
						((oracle.jdbc.OracleConnection) conn_tDBInput_1.unwrap(oracle.jdbc.OracleConnection.class))
								.setSessionTimeZone(sessionTimezone_tDBInput_1);
					}
				} else {
					((oracle.jdbc.OracleConnection) conn_tDBInput_1).setSessionTimeZone(sessionTimezone_tDBInput_1);
				}

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  SYSTEM.COUT_SOURCE.INSURER_NM, \n  SYSTEM.COUT_SOURCE.CLAIM_ID, \n  SYSTEM.COUT_SOURCE.CLAIM_CREATE_DT, \n  SYST"
						+ "EM.COUT_SOURCE.CURRENT_STATUS, \n  SYSTEM.COUT_SOURCE.CURRENT_SUBSTATUS, \n  SYSTEM.COUT_SOURCE.INVOICE_DATE, \n  SYSTEM.CO"
						+ "UT_SOURCE.OUTLET_NM, \n  SYSTEM.COUT_SOURCE.OUTLET_TYPE, \n  SYSTEM.COUT_SOURCE.OUTLET_CITY, \n  SYSTEM.COUT_SOURCE.OUTLET_"
						+ "PROVINCE, \n  SYSTEM.COUT_SOURCE.CLAIM_VEHICLE_YEAR, \n  SYSTEM.COUT_SOURCE.CLAIM_VEHICLE_MAKE, \n  SYSTEM.COUT_SOURCE.CLAI"
						+ "M_VEHICLE_MODEL, \n  SYSTEM.COUT_SOURCE.EVENT_STATUS, \n  SYSTEM.COUT_SOURCE.EVENT_SUBSTATUS, \n  SYSTEM.COUT_SOURCE.FIRST_"
						+ "TIME_IN_STATUS, \n  SYSTEM.COUT_SOURCE.TOTAL_HRS_IN_STATUS\nFROM SYSTEM.COUT_SOURCE";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.INSURER_NM = null;
						} else {

							row1.INSURER_NM = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.CLAIM_ID = null;
						} else {

							if (rs_tDBInput_1.getObject(2) != null) {
								row1.CLAIM_ID = rs_tDBInput_1.getBigDecimal(2);
							} else {

								row1.CLAIM_ID = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.CLAIM_CREATE_DT = null;
						} else {

							row1.CLAIM_CREATE_DT = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 3);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.CURRENT_STATUS = null;
						} else {

							row1.CURRENT_STATUS = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.CURRENT_SUBSTATUS = null;
						} else {

							row1.CURRENT_SUBSTATUS = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.INVOICE_DATE = null;
						} else {

							row1.INVOICE_DATE = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 6);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.OUTLET_NM = null;
						} else {

							row1.OUTLET_NM = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row1.OUTLET_TYPE = null;
						} else {

							row1.OUTLET_TYPE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, false);
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row1.OUTLET_CITY = null;
						} else {

							row1.OUTLET_CITY = routines.system.JDBCUtil.getString(rs_tDBInput_1, 9, false);
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row1.OUTLET_PROVINCE = null;
						} else {

							row1.OUTLET_PROVINCE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row1.CLAIM_VEHICLE_YEAR = null;
						} else {

							if (rs_tDBInput_1.getObject(11) != null) {
								row1.CLAIM_VEHICLE_YEAR = rs_tDBInput_1.getBigDecimal(11);
							} else {

								row1.CLAIM_VEHICLE_YEAR = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 12) {
							row1.CLAIM_VEHICLE_MAKE = null;
						} else {

							row1.CLAIM_VEHICLE_MAKE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, false);
						}
						if (colQtyInRs_tDBInput_1 < 13) {
							row1.CLAIM_VEHICLE_MODEL = null;
						} else {

							row1.CLAIM_VEHICLE_MODEL = routines.system.JDBCUtil.getString(rs_tDBInput_1, 13, false);
						}
						if (colQtyInRs_tDBInput_1 < 14) {
							row1.EVENT_STATUS = null;
						} else {

							row1.EVENT_STATUS = routines.system.JDBCUtil.getString(rs_tDBInput_1, 14, false);
						}
						if (colQtyInRs_tDBInput_1 < 15) {
							row1.EVENT_SUBSTATUS = null;
						} else {

							row1.EVENT_SUBSTATUS = routines.system.JDBCUtil.getString(rs_tDBInput_1, 15, false);
						}
						if (colQtyInRs_tDBInput_1 < 16) {
							row1.FIRST_TIME_IN_STATUS = null;
						} else {

							row1.FIRST_TIME_IN_STATUS = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 16);
						}
						if (colQtyInRs_tDBInput_1 < 17) {
							row1.TOTAL_HRS_IN_STATUS = null;
						} else {

							if (rs_tDBInput_1.getObject(17) != null) {
								row1.TOTAL_HRS_IN_STATUS = rs_tDBInput_1.getFloat(17);
							} else {

								row1.TOTAL_HRS_IN_STATUS = null;
							}
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tUniqRow_1 main ] start
						 */

						currentComponent = "tUniqRow_1";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row1"

							);
						}

						row2 = null;
						if (row1.OUTLET_NM == null) {
							finder_tUniqRow_1.OUTLET_NM = null;
						} else {
							finder_tUniqRow_1.OUTLET_NM = row1.OUTLET_NM.toLowerCase();
						}
						finder_tUniqRow_1.hashCodeDirty = true;
						if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
							KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

							if (row1.OUTLET_NM == null) {
								new_tUniqRow_1.OUTLET_NM = null;
							} else {
								new_tUniqRow_1.OUTLET_NM = row1.OUTLET_NM.toLowerCase();
							}

							keystUniqRow_1.add(new_tUniqRow_1);
							if (row2 == null) {

								row2 = new row2Struct();
							}
							row2.INSURER_NM = row1.INSURER_NM;
							row2.CLAIM_ID = row1.CLAIM_ID;
							row2.CLAIM_CREATE_DT = row1.CLAIM_CREATE_DT;
							row2.CURRENT_STATUS = row1.CURRENT_STATUS;
							row2.CURRENT_SUBSTATUS = row1.CURRENT_SUBSTATUS;
							row2.INVOICE_DATE = row1.INVOICE_DATE;
							row2.OUTLET_NM = row1.OUTLET_NM;
							row2.OUTLET_TYPE = row1.OUTLET_TYPE;
							row2.OUTLET_CITY = row1.OUTLET_CITY;
							row2.OUTLET_PROVINCE = row1.OUTLET_PROVINCE;
							row2.CLAIM_VEHICLE_YEAR = row1.CLAIM_VEHICLE_YEAR;
							row2.CLAIM_VEHICLE_MAKE = row1.CLAIM_VEHICLE_MAKE;
							row2.CLAIM_VEHICLE_MODEL = row1.CLAIM_VEHICLE_MODEL;
							row2.EVENT_STATUS = row1.EVENT_STATUS;
							row2.EVENT_SUBSTATUS = row1.EVENT_SUBSTATUS;
							row2.FIRST_TIME_IN_STATUS = row1.FIRST_TIME_IN_STATUS;
							row2.TOTAL_HRS_IN_STATUS = row1.TOTAL_HRS_IN_STATUS;
							nb_uniques_tUniqRow_1++;
						} else {
							nb_duplicates_tUniqRow_1++;
						}

						tos_count_tUniqRow_1++;

						/**
						 * [tUniqRow_1 main ] stop
						 */

						/**
						 * [tUniqRow_1 process_data_begin ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_begin ] stop
						 */
// Start of branch "row2"
						if (row2 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row2"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								dim_outlet = null;

// # Output table : 'dim_outlet'
								dim_outlet_tmp.outlet_key = Numeric.sequence("s1", 1, 1);
								dim_outlet_tmp.Nom = row1.OUTLET_NM;
								dim_outlet_tmp.Type = row1.OUTLET_TYPE;
								dim_outlet_tmp.City = row1.OUTLET_CITY;
								dim_outlet_tmp.Province = row1.OUTLET_PROVINCE;
								dim_outlet = dim_outlet_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "dim_outlet"
							if (dim_outlet != null) {

								/**
								 * [tDBOutput_1 main ] start
								 */

								currentComponent = "tDBOutput_1";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "dim_outlet"

									);
								}

								whetherReject_tDBOutput_1 = false;
								if (dim_outlet.outlet_key == null) {
									pstmt_tDBOutput_1.setNull(1, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(1, dim_outlet.outlet_key);
								}

								if (dim_outlet.Nom == null) {
									pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(2, dim_outlet.Nom);
								}

								if (dim_outlet.Type == null) {
									pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(3, dim_outlet.Type);
								}

								if (dim_outlet.City == null) {
									pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(4, dim_outlet.City);
								}

								if (dim_outlet.Province == null) {
									pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(5, dim_outlet.Province);
								}

								pstmt_tDBOutput_1.addBatch();
								nb_line_tDBOutput_1++;
								batchSizeCounter_tDBOutput_1++;
								if (batchSize_tDBOutput_1 > 0
										&& batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1) {
									try {
										pstmt_tDBOutput_1.executeBatch();
									} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
										java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
												sqle_tDBOutput_1 = null;
										String errormessage_tDBOutput_1;
										if (ne_tDBOutput_1 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_1 = new java.sql.SQLException(
													e_tDBOutput_1.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_1.getMessage(),
													ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
													ne_tDBOutput_1);
											errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
										} else {
											errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
										}

										System.err.println(errormessage_tDBOutput_1);

									}
									tmp_batchUpdateCount_tDBOutput_1 = pstmt_tDBOutput_1.getUpdateCount();
									insertedCount_tDBOutput_1 += (tmp_batchUpdateCount_tDBOutput_1 != -1
											? tmp_batchUpdateCount_tDBOutput_1
											: 0);
									rowsToCommitCount_tDBOutput_1 += (tmp_batchUpdateCount_tDBOutput_1 != -1
											? tmp_batchUpdateCount_tDBOutput_1
											: 0);
									batchSizeCounter_tDBOutput_1 = 0;
								}
								commitCounter_tDBOutput_1++;
								if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {
									if (batchSizeCounter_tDBOutput_1 > 0) {
										try {
											pstmt_tDBOutput_1.executeBatch();
										} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
											globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
											java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
													sqle_tDBOutput_1 = null;
											String errormessage_tDBOutput_1;
											if (ne_tDBOutput_1 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_1 = new java.sql.SQLException(
														e_tDBOutput_1.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_1.getMessage(),
														ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
														ne_tDBOutput_1);
												errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
											} else {
												errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
											}

											System.err.println(errormessage_tDBOutput_1);

										}
										tmp_batchUpdateCount_tDBOutput_1 = pstmt_tDBOutput_1.getUpdateCount();
										insertedCount_tDBOutput_1 += (tmp_batchUpdateCount_tDBOutput_1 != -1
												? tmp_batchUpdateCount_tDBOutput_1
												: 0);
										rowsToCommitCount_tDBOutput_1 += (tmp_batchUpdateCount_tDBOutput_1 != -1
												? tmp_batchUpdateCount_tDBOutput_1
												: 0);
									}
									if (rowsToCommitCount_tDBOutput_1 != 0) {

									}
									conn_tDBOutput_1.commit();
									if (rowsToCommitCount_tDBOutput_1 != 0) {

										rowsToCommitCount_tDBOutput_1 = 0;
									}
									commitCounter_tDBOutput_1 = 0;
									batchSizeCounter_tDBOutput_1 = 0;
								}

								tos_count_tDBOutput_1++;

								/**
								 * [tDBOutput_1 main ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_end ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_end ] stop
								 */

							} // End of branch "dim_outlet"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row2"

						/**
						 * [tUniqRow_1 process_data_end ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}

				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				if (batchSizeCounter_tDBOutput_1 > 0) {
					try {
						if (pstmt_tDBOutput_1 != null) {

							pstmt_tDBOutput_1.executeBatch();

						}
					} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
						globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
						java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
								sqle_tDBOutput_1 = null;
						String errormessage_tDBOutput_1;
						if (ne_tDBOutput_1 != null) {
							// build new exception to provide the original cause
							sqle_tDBOutput_1 = new java.sql.SQLException(
									e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(),
									ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
							errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
						} else {
							errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
						}

						System.err.println(errormessage_tDBOutput_1);

					}
					if (pstmt_tDBOutput_1 != null) {
						tmp_batchUpdateCount_tDBOutput_1 = pstmt_tDBOutput_1.getUpdateCount();

						insertedCount_tDBOutput_1

								+= (tmp_batchUpdateCount_tDBOutput_1 != -1 ? tmp_batchUpdateCount_tDBOutput_1 : 0);
						rowsToCommitCount_tDBOutput_1 += (tmp_batchUpdateCount_tDBOutput_1 != -1
								? tmp_batchUpdateCount_tDBOutput_1
								: 0);
					}
				}
				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");

				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

				}
				conn_tDBOutput_1.commit();
				if (commitCounter_tDBOutput_1 > 0 && rowsToCommitCount_tDBOutput_1 != 0) {

					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				conn_tDBOutput_1.close();

				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "dim_outlet");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								ctn_tDBOutput_1.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final Loading_dim Loading_dimClass = new Loading_dim();

		int exitCode = Loading_dimClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = Loading_dim.class.getClassLoader()
					.getResourceAsStream("projet11/loading_dim_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = Loading_dim.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Loading_dim");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 93539 characters generated by Talend Open Studio for Data Integration on the
 * 4 janvier 2023 à 21:11:42 WEST
 ************************************************************************************************/