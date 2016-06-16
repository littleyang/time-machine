package com.time.mapreduce.test.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class ProjectAndStaffString implements WritableComparable<ProjectAndStaffString>{
	
	private String value;

	private int flag = 0;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	public ProjectAndStaffString() {  
        super();  
        // TODO Auto-generated constructor stub  
    }  
	
	public ProjectAndStaffString(String value,int flag){
		this.value = value;
		this.flag = flag;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(flag);  
        out.writeUTF(value);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		 this.flag = in.readInt();  
	     this.value = in.readUTF();  
	}

	@Override
	public int compareTo(ProjectAndStaffString o) {
		// TODO Auto-generated method stub
		if (this.flag >= o.getFlag()) {  
            if (this.flag > o.getFlag()) {  
                return 1;  
            }  
        } else {  
            return -1;  
        }  
        return this.value.compareTo(o.getValue());  
	}

}
