package com.dodogco.hashing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;


public class VirtualFileImp extends UnicastRemoteObject
implements VirtualFile {

	private static final long serialVersionUID = -4842526464612109139L;
	private File file;
	private String hash;

	public VirtualFileImp(String path) throws RemoteException {
		super();
		this.file = new File(path);
		try {
			loadHash();
		} catch (NoSuchAlgorithmException | IOException e) {
			System.err.println("Failure on loading MD5 Hash");
			e.printStackTrace();
			this.hash = "null";
		}
	}

	private void loadHash() throws NoSuchAlgorithmException, IOException {
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
	        hash = DigestUtils.md5Hex(fis).toUpperCase();
		} finally {
			if(fis != null) fis.close();
		}
		
	}

	@Override
	public File getFile() throws RemoteException {
		return file;
	}

	@Override
	public String getMD5() throws RemoteException {
		return hash;
	}

	@Override
	public String toString() {
		return "VirtualFileImp(" + file.getPath() +", hash: "+ hash+")";
	}

}
