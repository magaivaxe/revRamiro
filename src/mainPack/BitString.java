package mainPack;

import java.util.Arrays;


class BSInvalidSizeException extends RuntimeException {
    public int data;
    BSInvalidSizeException(int s){
            this.data = s;
    }
    
    @Override
    public String getMessage(){
            return "Invalid size: " + data; 
    }	
}

class BSOutOfBoundsException extends RuntimeException {
    public int data;
    BSOutOfBoundsException(int s){
            this.data = s;
    }
    
    @Override
    public String getMessage(){ 
            return "Index out of bounds: "+ data; 
    }	
}

class BSInvalidRangeException extends RuntimeException {
    public int data1, data2, flag;
    
    BSInvalidRangeException(int a, int b, int f) {
        this.data1 = a;
        this.data2 = b;
        this.flag = f;
    }
    
    @Override
    public String getMessage() {
        switch (flag){
            case 0:
                return "Invalid initial position: " + data1;
            case 1:
                return "Invalid final position: " + data2;
            case 2:
                return "Initial position "+ data1
                        + " greater than final position " + data2;
            default:
                return "Unknow invalid range code";
        }
            
    }
}

public class BitString {
    private byte[] bsdata;
    private byte t = 0x71;
    private byte f = 0x70;

    public BitString() {
        bsdata = new byte[0];		
    }

    public BitString(int len) throws BSInvalidSizeException {
        if (len < 0){throw new BSInvalidSizeException(len) ;}
        bsdata = new byte[len];		
    }
    public int length(){
        return(bsdata.length);
    }

    public void resize(int size) throws BSInvalidSizeException {
        if (size < 0) { throw new BSInvalidSizeException(size) ;}
        bsdata = Arrays.copyOf(bsdata, size); 
    }

    public boolean get(int pos) throws BSOutOfBoundsException {
        if ((pos < 0)||(pos >= bsdata.length))
        {throw new BSOutOfBoundsException(pos);}		
        return(t == bsdata[pos]);				
    }

    public void set(int pos, boolean v) throws BSOutOfBoundsException
    {
        if ((pos < 0) || (pos >= bsdata.length)) {
            throw new BSOutOfBoundsException(pos);
        }
        bsdata[pos] = v ? t: f;
    }
    public BitString append(boolean v)
    {
        BitString w = new BitString(this.bsdata.length +1);
        System.arraycopy(this.bsdata, 0, w.bsdata, 0, this.bsdata.length);
        if (v) { w.bsdata[w.bsdata.length - 1] = t;}
        else {w.bsdata[w.bsdata.length - 1] = f;}
        return w;
    }
    
    public BitString concat(BitString BS1) 
    {
        if (BS1 == null) { BS1 = new BitString(0); }
        BitString w = new BitString(this.bsdata.length + BS1.bsdata.length);
        System.arraycopy(this.bsdata, 0, w.bsdata, 0, this.bsdata.length);
        System.arraycopy(BS1.bsdata, 0, w.bsdata,
                         this.bsdata.length, BS1.bsdata.length);
        return w;
    }
    
    public BitString subBitString(int init, int fin) 
                                  throws BSInvalidRangeException {
        if (init < 0) {
            throw new BSInvalidRangeException(init, fin, 0);
        }
        else if (fin > bsdata.length) {
            throw new BSInvalidRangeException(init, fin, 1);
        }
        else if (fin < init) {
            throw new BSInvalidRangeException(init, fin, 2);
        }
        BitString w = new BitString(fin - init);
        System.arraycopy(bsdata, init, w.bsdata, 0, fin - init);
        return  w;
    }
    
    public boolean equals(BitString BS1){
        return Arrays.equals(this.bsdata, BS1.bsdata);
    }
            
}


