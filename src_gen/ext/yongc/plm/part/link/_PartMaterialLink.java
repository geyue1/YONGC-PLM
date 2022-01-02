package ext.yongc.plm.part.link;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PartMaterialLink extends wt.fc.Item implements wt.inf.container.WTContained, java.io.Externalizable, wt.type.Typed {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.yongc.plm.part.link.linkResource";
   static final java.lang.String CLASSNAME = PartMaterialLink.class.getName();

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_AIDA2A2 = "roleAida2a2";
   static int ROLE_AIDA2A2_UPPER_LIMIT = -1;
   java.lang.String roleAida2a2;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleAida2a2() {
      return roleAida2a2;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleAida2a2(java.lang.String roleAida2a2) throws wt.util.WTPropertyVetoException {
      roleAida2a2Validate(roleAida2a2);
      this.roleAida2a2 = roleAida2a2;
   }
   void roleAida2a2Validate(java.lang.String roleAida2a2) throws wt.util.WTPropertyVetoException {
      if (roleAida2a2 == null || roleAida2a2.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAida2a2") },
               new java.beans.PropertyChangeEvent(this, "roleAida2a2", this.roleAida2a2, roleAida2a2));
      if (ROLE_AIDA2A2_UPPER_LIMIT < 1) {
         try { ROLE_AIDA2A2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAida2a2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_AIDA2A2_UPPER_LIMIT = 50; }
      }
      if (roleAida2a2 != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAida2a2.toString(), ROLE_AIDA2A2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAida2a2"), java.lang.String.valueOf(java.lang.Math.min(ROLE_AIDA2A2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAida2a2", this.roleAida2a2, roleAida2a2));
   }

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_BMASTERIDA2A2 = "roleBmasterida2a2";
   static int ROLE_BMASTERIDA2A2_UPPER_LIMIT = -1;
   java.lang.String roleBmasterida2a2;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleBmasterida2a2() {
      return roleBmasterida2a2;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleBmasterida2a2(java.lang.String roleBmasterida2a2) throws wt.util.WTPropertyVetoException {
      roleBmasterida2a2Validate(roleBmasterida2a2);
      this.roleBmasterida2a2 = roleBmasterida2a2;
   }
   void roleBmasterida2a2Validate(java.lang.String roleBmasterida2a2) throws wt.util.WTPropertyVetoException {
      if (roleBmasterida2a2 == null || roleBmasterida2a2.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBmasterida2a2") },
               new java.beans.PropertyChangeEvent(this, "roleBmasterida2a2", this.roleBmasterida2a2, roleBmasterida2a2));
      if (ROLE_BMASTERIDA2A2_UPPER_LIMIT < 1) {
         try { ROLE_BMASTERIDA2A2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBmasterida2a2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BMASTERIDA2A2_UPPER_LIMIT = 50; }
      }
      if (roleBmasterida2a2 != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBmasterida2a2.toString(), ROLE_BMASTERIDA2A2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBmasterida2a2"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BMASTERIDA2A2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBmasterida2a2", this.roleBmasterida2a2, roleBmasterida2a2));
   }

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_ANUMBER = "roleAnumber";
   static int ROLE_ANUMBER_UPPER_LIMIT = -1;
   java.lang.String roleAnumber;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleAnumber() {
      return roleAnumber;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleAnumber(java.lang.String roleAnumber) throws wt.util.WTPropertyVetoException {
      roleAnumberValidate(roleAnumber);
      this.roleAnumber = roleAnumber;
   }
   void roleAnumberValidate(java.lang.String roleAnumber) throws wt.util.WTPropertyVetoException {
      if (roleAnumber == null || roleAnumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAnumber") },
               new java.beans.PropertyChangeEvent(this, "roleAnumber", this.roleAnumber, roleAnumber));
      if (ROLE_ANUMBER_UPPER_LIMIT < 1) {
         try { ROLE_ANUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAnumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_ANUMBER_UPPER_LIMIT = 50; }
      }
      if (roleAnumber != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAnumber.toString(), ROLE_ANUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAnumber"), java.lang.String.valueOf(java.lang.Math.min(ROLE_ANUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAnumber", this.roleAnumber, roleAnumber));
   }

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_ANAME = "roleAname";
   static int ROLE_ANAME_UPPER_LIMIT = -1;
   java.lang.String roleAname;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleAname() {
      return roleAname;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleAname(java.lang.String roleAname) throws wt.util.WTPropertyVetoException {
      roleAnameValidate(roleAname);
      this.roleAname = roleAname;
   }
   void roleAnameValidate(java.lang.String roleAname) throws wt.util.WTPropertyVetoException {
      if (roleAname == null || roleAname.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAname") },
               new java.beans.PropertyChangeEvent(this, "roleAname", this.roleAname, roleAname));
      if (ROLE_ANAME_UPPER_LIMIT < 1) {
         try { ROLE_ANAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleAname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_ANAME_UPPER_LIMIT = 50; }
      }
      if (roleAname != null && !wt.fc.PersistenceHelper.checkStoredLength(roleAname.toString(), ROLE_ANAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleAname"), java.lang.String.valueOf(java.lang.Math.min(ROLE_ANAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleAname", this.roleAname, roleAname));
   }

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_BNUMBER = "roleBnumber";
   static int ROLE_BNUMBER_UPPER_LIMIT = -1;
   java.lang.String roleBnumber;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleBnumber() {
      return roleBnumber;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleBnumber(java.lang.String roleBnumber) throws wt.util.WTPropertyVetoException {
      roleBnumberValidate(roleBnumber);
      this.roleBnumber = roleBnumber;
   }
   void roleBnumberValidate(java.lang.String roleBnumber) throws wt.util.WTPropertyVetoException {
      if (roleBnumber == null || roleBnumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBnumber") },
               new java.beans.PropertyChangeEvent(this, "roleBnumber", this.roleBnumber, roleBnumber));
      if (ROLE_BNUMBER_UPPER_LIMIT < 1) {
         try { ROLE_BNUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBnumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BNUMBER_UPPER_LIMIT = 50; }
      }
      if (roleBnumber != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBnumber.toString(), ROLE_BNUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBnumber"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BNUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBnumber", this.roleBnumber, roleBnumber));
   }

   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public static final java.lang.String ROLE_BNAME = "roleBname";
   static int ROLE_BNAME_UPPER_LIMIT = -1;
   java.lang.String roleBname;
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public java.lang.String getRoleBname() {
      return roleBname;
   }
   /**
    * @see ext.yongc.plm.part.link.PartMaterialLink
    */
   public void setRoleBname(java.lang.String roleBname) throws wt.util.WTPropertyVetoException {
      roleBnameValidate(roleBname);
      this.roleBname = roleBname;
   }
   void roleBnameValidate(java.lang.String roleBname) throws wt.util.WTPropertyVetoException {
      if (roleBname == null || roleBname.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBname") },
               new java.beans.PropertyChangeEvent(this, "roleBname", this.roleBname, roleBname));
      if (ROLE_BNAME_UPPER_LIMIT < 1) {
         try { ROLE_BNAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("roleBname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROLE_BNAME_UPPER_LIMIT = 50; }
      }
      if (roleBname != null && !wt.fc.PersistenceHelper.checkStoredLength(roleBname.toString(), ROLE_BNAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "roleBname"), java.lang.String.valueOf(java.lang.Math.min(ROLE_BNAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "roleBname", this.roleBname, roleBname));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
   }

   wt.type.TypeDefinitionReference typeDefinitionReference;
   /**
    * @see wt.type.Typed
    */
   public wt.type.TypeDefinitionReference getTypeDefinitionReference() {
      return typeDefinitionReference;
   }
   /**
    * @see wt.type.Typed
    */
   public void setTypeDefinitionReference(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      typeDefinitionReferenceValidate(typeDefinitionReference);
      this.typeDefinitionReference = typeDefinitionReference;
   }
   void typeDefinitionReferenceValidate(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      if (typeDefinitionReference == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeDefinitionReference") },
               new java.beans.PropertyChangeEvent(this, "typeDefinitionReference", this.typeDefinitionReference, typeDefinitionReference));
   }

   wt.iba.value.AttributeContainer theAttributeContainer;
   /**
    * @see wt.iba.value.IBAHolder
    */
   public wt.iba.value.AttributeContainer getAttributeContainer() {
      return theAttributeContainer;
   }
   /**
    * @see wt.iba.value.IBAHolder
    */
   public void setAttributeContainer(wt.iba.value.AttributeContainer theAttributeContainer) {
      this.theAttributeContainer = theAttributeContainer;
   }

   public java.lang.String getConceptualClassname() {
      return CLASSNAME;
   }

   public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
      return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
   }

   public java.lang.String getType() {
      try { return getClassInfo().getDisplayName(); }
      catch (wt.introspection.WTIntrospectionException wte) { return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.'); }
   }

   public static final long EXTERNALIZATION_VERSION_UID = 6350551231472290580L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( containerReference );
      output.writeObject( roleAida2a2 );
      output.writeObject( roleAname );
      output.writeObject( roleAnumber );
      output.writeObject( roleBmasterida2a2 );
      output.writeObject( roleBname );
      output.writeObject( roleBnumber );
      output.writeObject( typeDefinitionReference );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( theAttributeContainer );
      }

   }

   protected void super_writeExternal_PartMaterialLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.yongc.plm.part.link.PartMaterialLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_PartMaterialLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "roleAida2a2", roleAida2a2 );
      output.setString( "roleAname", roleAname );
      output.setString( "roleAnumber", roleAnumber );
      output.setString( "roleBmasterida2a2", roleBmasterida2a2 );
      output.setString( "roleBname", roleBname );
      output.setString( "roleBnumber", roleBnumber );
      output.writeObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      roleAida2a2 = input.getString( "roleAida2a2" );
      roleAname = input.getString( "roleAname" );
      roleAnumber = input.getString( "roleAnumber" );
      roleBmasterida2a2 = input.getString( "roleBmasterida2a2" );
      roleBname = input.getString( "roleBname" );
      roleBnumber = input.getString( "roleBnumber" );
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
   }

   boolean readVersion6350551231472290580L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      roleAida2a2 = (java.lang.String) input.readObject();
      roleAname = (java.lang.String) input.readObject();
      roleAnumber = (java.lang.String) input.readObject();
      roleBmasterida2a2 = (java.lang.String) input.readObject();
      roleBname = (java.lang.String) input.readObject();
      roleBnumber = (java.lang.String) input.readObject();
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            theAttributeContainer = (wt.iba.value.AttributeContainer) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( PartMaterialLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6350551231472290580L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_PartMaterialLink( _PartMaterialLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
