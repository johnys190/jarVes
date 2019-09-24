import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { renderID } from '../util/Helpers';
import { 
    Radio,
    Input,
    DatePicker,
    Select,
    Button,
    Row,
    Col,
    notification,
    Checkbox
} from 'antd';
import { 
    getAllVessels,
    getVoyEstimateById,
    updateVoyEstimateById,
    createVoyEstimate,
    getTXT,
    getPDF
} from '../util/APIUtils';
import moment from "moment";

const Option = Select.Option;

class VoyageEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            vessels: [],
            voyest:{
                id: this.props.id ? this.props.id : 'new',
                account: '',
                broker: '',
                commodity: '',
                name: '',
                vessel: null,
                voyage: '',
                executed: false
            }

        };
        this.getAllVessels();
        //this.getAllVessels = this.getAllVessels.bind(this);
        if (this.state.voyest.id !== 'new'){
            this.getVoyageEstimate();
        }
    }

    componentWillMount(){
        this.getAllVessels();
    }

    calculate(){
        const voyestEdit = this.state.voyest;

        let total_ballast_distance = Number(voyestEdit.non_seca_ballast) + Number(voyestEdit.seca_ballast);
        let total_laden_distance = Number(voyestEdit.non_seca_laden) + Number(voyestEdit.seca_laden);

        if ( voyestEdit.freight_rate_type == 'lumpsum' ) {
            voyestEdit.gross_revenue = voyestEdit.freight_rate;
        } else { 
            voyestEdit.gross_revenue = voyestEdit.freight_rate * voyestEdit.quantity;
        }       

        voyestEdit.sailing_bunkers = (total_ballast_distance - voyestEdit.seca_ballast) * voyestEdit.ifo_ballast * voyestEdit.ifo_price + voyestEdit.seca_ballast * voyestEdit.ifo_ballast * voyestEdit.mgo_price + (total_laden_distance - voyestEdit.seca_laden) * voyestEdit.ifo_laden * voyestEdit.ifo_price + voyestEdit.seca_laden * voyestEdit.ifo_laden * voyestEdit.mgo_price;
        
        if (voyestEdit.load_port_type == 'seca'){
            voyestEdit.loadport_bunkers = voyestEdit.ifo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + voyestEdit.mgo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + (voyestEdit.load_days + voyestEdit.shex_load) * voyestEdit.boiler_port * voyestEdit.mgo_price;
        }

        if (voyestEdit.load_port_type == 'non_seca'){
            voyestEdit.loadport_bunkers = voyestEdit.ifo_port_work * voyestEdit.load_days * voyestEdit.ifo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_load * voyestEdit.ifo_price + voyestEdit.mgo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + (voyestEdit.load_days + voyestEdit.shex_load) * voyestEdit.boiler_port * voyestEdit.ifo_price;
        }

        if (voyestEdit.discharge_port_type  == 'seca'){
            voyestEdit.disport_bunkers = voyestEdit.ifo_port_work * voyestEdit.disch_days * voyestEdit.mgo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_disch * voyestEdit.mgo_price + voyestEdit.mgo_port_work * voyestEdit.disch_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_disch * voyestEdit.mgo_price + (voyestEdit.disch_days + voyestEdit.shex_disch) * voyestEdit.boiler_port * voyestEdit.mgo_price;
        }
        
        if (voyestEdit.discharge_port_type  == 'non_seca'){
            voyestEdit.disport_bunkers = voyestEdit.ifo_port_work * voyestEdit.disch_days * voyestEdit.ifo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_disch * voyestEdit.ifo_price + voyestEdit.mgo_port_work * voyestEdit.disch_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_disch * voyestEdit.mgo_price + (voyestEdit.disch_days + voyestEdit.shex_disch) * voyestEdit.boiler_port * voyestEdit.ifo_price;
        }

        voyestEdit.total_bunker_cost = voyestEdit.loadport_bunkers + voyestEdit.disport_bunkers + voyestEdit.sailing_bunkers + voyestEdit.lostwaiting_days * ( voyestEdit.ifo_port_idle * voyestEdit.ifo_price + voyestEdit.mgo_port_idle * voyestEdit.mgo_price + voyestEdit.boiler_port * voyestEdit.ifo_price)
        

        voyestEdit.expenses = Number(voyestEdit.load) + Number(voyestEdit.disch) + Number(voyestEdit.others) + Number(voyestEdit.canals) + Number(voyestEdit.exins) + (Number(voyestEdit.comm) * Number(voyestEdit.gross_revenue));
        

        voyestEdit.taxes = voyestEdit.taxesP * voyestEdit.gross_revenue;
        

        voyestEdit.net_revenue = voyestEdit.gross_revenue - voyestEdit.expenses - voyestEdit.taxes - voyestEdit.total_bunker_cost;
        

        voyestEdit.time_charter_rate = voyestEdit.net_revenue / voyestEdit.total_duration;

        voyestEdit.steaming = (Number(total_ballast_distance) + Number(total_laden_distance) ) / ( voyestEdit.speed * 24 ) + voyestEdit.steaming_margin * (Number(total_ballast_distance) + Number(total_laden_distance) )  / ( voyestEdit.speed * 24 );
        
        if ( voyestEdit.load_rate_type == 'daps' ) { 
            voyestEdit.load_days = voyestEdit.lrate;
        } else { 
            voyestEdit.load_days = voyestEdit.quantity / voyestEdit.lrate; 
        }

        if ( voyestEdit.discharge_rate_type == 'daps' ) { 
            voyestEdit.disch_days = voyestEdit.drate;
        } else { 
            voyestEdit.disch_days = voyestEdit.quantity / voyestEdit.drate;
        }

        if ( voyestEdit.load_rate_type == 'x') {
            voyestEdit.shex_load = voyestEdit.load_days * voyestEdit.lostwaiting_days;
        } else { 
            voyestEdit.shex_load = 1; 
        } 
   
        if ( voyestEdit.discharge_rate_type == 'x') { 
            voyestEdit.shex_disch = voyestEdit.disch_days * voyestEdit.lostwaiting_days;
        } else { 
            voyestEdit.shex_disch = 1; 
        } 

        voyestEdit.total_duration = Number(voyestEdit.steaming) + Number(voyestEdit.load_days) + Number(voyestEdit.disch_days) + Number(voyestEdit.shex_load) + Number(voyestEdit.shex_disch) + Number(voyestEdit.lostwaiting_days);
    
        this.setState({
            voyest:voyestEdit
        });
    }

    getAllVessels(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllVessels();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    vessels: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    getVoyageEstimate(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getVoyEstimateById(this.state.voyest.id);

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    voyest:response,
                    isLoading: false
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    handleSubmit() {
        this.setState({
            isLoading: true,
        });

        let voyest = this.state.voyest;
        let promise;
        if (this.state.voyest.id !== 'new'){

            promise = updateVoyEstimateById(this.state.voyest.id, voyest);
            promise
                .then(response => {
                    notification.success({
                        message: 'Link Line Voyage Estimate',
                        description: "Sucessfully saved changes!",
                    });
                    this.setState({
                        isLoading: false
                    });
                })
                .catch(error => {
                    notification.error({
                        message: 'Link Line Voyage Estimate',
                        description: error.message || 'Sorry! Something went wrong. Please try again!'
                    });
                    this.setState({
                        isLoading: false
                    });
                });
        }else{
            delete voyest['id'];
            promise = createVoyEstimate(voyest);
            promise
                .then(response => {
                    notification.success({
                        message: 'Link Line Voyage Estimate',
                        description: "Sucessfully created!",
                    });

                    this.setState({
                        voyest: response,
                        isLoading: false
                    });
					this.props.history.push("/voyageEstimate/"+response.id);
                })
                .catch(error => {
                    notification.error({
                        message: 'Link Line Voyage Estimate',
                        description: error.message || 'Sorry! Something went wrong. Please try again!'
                    });
                    this.setState({
                        isLoading: false
                    });
                });
        }
    }

    handleSubmitAs() {
        this.setState({
            isLoading: true,
        });

        let voyest = this.state.voyest;
        let curName = voyest.name;
        voyest.name = voyest.save_as;
        delete voyest['id'];
        let promise;
        promise = createVoyEstimate(voyest);
        promise
            .then(response => {
                notification.success({
                    message: 'Link Line Voyage Estimate',
                    description: "Sucessfully saved!",
                });
                this.setState({
                    voyest: response,
                    isLoading: false
                });
				this.props.history.push("/voyageEstimate/"+response.id);
            })
            .catch(error => {
                notification.error({
                    message: 'Link Line Voyage Estimate',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
                this.setState({
                    isLoading: false
                });
            });
    }
    handleTXT = () => {
        let me;
        me = getTXT(this.state.voyest.id, "voyage-estimate-" + this.state.voyest.name.replace(/\s+/g, '-').toLowerCase() + ".txt");
        if (!me) {
            return;
        }
    };

    handlePDF = () => {
        let me;
        me = getPDF(this.state.voyest.id, "voyage-estimate-" + this.state.voyest.name.replace(/\s+/g, '-').toLowerCase() + ".pdf");
        if (!me) {
            return;
        }
    };

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        const voyestEdit = this.state.voyest;
        voyestEdit[inputName]= inputValue;
        this.setState({
            voyest:voyestEdit
        });
        this.calculate();
    }

    handleDateChange(date,dateString){
        console.log(date);
        console.log(dateString);
        const voyestEdit = this.state.voyest;
        voyestEdit['date']= dateString;
        this.setState({
            voyest:voyestEdit
        });

    }

    // handleChangeInput = (e) => {
    //     const id = e.target.id;
    //     const value = e.target.value;
    //     this.setState({
    //         [id]: value
    //         },
    //         this.calculate
    //     );
    // }

    // handleChangeRadio = (e) => {
    //     const id = e.target.name;
    //     const value = e.target.value;
    //     this.setState({
    //         [id]: value
    //         },
    //     );
    // }

    handleChangeSelect = (id) => {
        let voyestEdit = this.state.voyest;
        let vessel = this.state.vessels.find( (vessel) => {return vessel.id == id});
        Object.keys(vessel).forEach(function(key) {
            if (key == 'name'){
                voyestEdit['vessel_name'] = vessel[key];
            }else if (key == 'id'){
                voyestEdit['vessel_id'] = vessel[key];
            }else if (key == 'port_idle'){
                voyestEdit['ifo_port_idle'] = vessel[key];
            }else if (key == 'port_working'){
                voyestEdit['ifo_port_work'] = vessel[key];
            }else if (key == 'mgo_port_working'){
                voyestEdit['mgo_port_work'] = vessel[key];
            }else if (key == 'boiler'){
                voyestEdit['boiler_port'] = vessel[key];
            }else{
                voyestEdit[key]= vessel[key];
            }
        });
        console.log(voyestEdit);
        console.log(vessel);
        this.setState({
            voyest: voyestEdit
        });
    }

    handleNewCancelClick = () => {
        this.setState({
            new: !this.state.new
        });
    }

    handleCheckbox(event){
        const target = event.target;
        const inputName = 'executed';
        const inputValue = target.checked;
        const voyestEdit = this.state.voyest;
        voyestEdit[inputName]= inputValue;
        this.setState({
            voyest:voyestEdit
        });
    }

    renderInputList(fields, className='alignComponent', disabled=false){
        return fields.map( (field) => (
             <Input 
                className={className}
                addonBefore={field} 
                name={renderID(field)}
                onChange={(event) => this.handleInputChange(event)}
                disabled={disabled}
                value={this.state.voyest[renderID(field)]}/> 
        ))
    }

    renderInputNumberList(fields, className='alignComponent', disabled=false){
        return fields.map( (field) => (
             <Input 
                type='number'
                step='any'
                className={className}
                addonBefore={field} 
                name={renderID(field)}
                onChange={(event) => this.handleInputChange(event)}
                disabled={disabled}
                value={this.state.voyest[renderID(field)]}/> 
        ))
    }


    renderRadioList(fields, id, style={}, className='alignRadioGroup'){
        const RadioGroup = Radio.Group;
        return (
            <RadioGroup 
                className={className} 
                name={id}
                value={this.state.voyest[id]}
                onChange={this.handleInputChange.bind(this)}>
                    {fields.map( (field) => (
                        <Radio  
                            style={style} 
                            value={renderID(field)}>
                                {field}
                        </Radio> 
                    ))}
            </RadioGroup>
        )
    }
	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }
        const veerticalRadioStyle = {
            display: 'block',
            height: '30px',
            lineHeight: '30px',
        };

        const vesselsOptions = this.state.vessels.map((vessel) => {
                                        return (<Option value={vessel.id}>{vessel.name}</Option>);
                                    });
        const saveAs = (this.state.voyest.id && this.state.voyest.id !== 'new') ?
            (<div>
				<Row gutter={15}>
					<Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                		<Input addonBefore='Save as' name='save_as' className='alignComponent'  onChange={(event) => this.handleInputChange(event)} value={this.state.voyest.save_as} style={{marginTop: '1%', marginBottom: '1%'}}/>
					</Col>
					<Col  xs={24} sm={12} md={12} lg={12} xl={12}>
						<Button className='saveButton' type='primary' onClick={this.handleSubmitAs.bind(this)}>Save as</Button>
					</Col>
				</Row>
            </div>) : '';
        const downloadButtons = (this.state.voyest.id && this.state.voyest.id !== 'new') ?
            (<div>
                <br />
                <br />
                <Row gutter={15}>
                    <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                        <div style={{textAlign: 'center'}}>
                            <Button className='buttonDL' type='primary' onClick={this.handleTXT.bind(this)}>Download Text</Button>
                        </div>
                    </Col>
                    <Col  xs={24} sm={12} md={12} lg={12} xl={12} >
                        <div style={{textAlign: 'center'}}>
                            <Button className='buttonDL' type='primary' onClick={this.handlePDF.bind(this)}>Download PDF</Button>
                        </div>
                    </Col>
                </Row>
            </div>) : '';
		return (
            <div>
                <Row gutter={15}>
                    <Col  xs={24} sm={24} md={24} lg={24} xl={12}>
						<Row gutter={15}>
							<Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                        		<Input addonBefore='Name' name='name' className='alignComponent' value={this.state.voyest.name}  onChange={(event) => this.handleInputChange(event)} style={{marginTop: '1%', marginBottom: '1%'}}/>
							</Col>
							<Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                        		<Button className='saveButton' type='primary' onClick={this.handleSubmit.bind(this)}>Save</Button>
							</Col>
						</Row>
                        {saveAs}
						<br />
						<Row gutter={15} >
							<Col>
								<Checkbox className='alignSelect' checked={this.state.voyest.executed} onChange={(event) => this.handleCheckbox(event)} >Executed </Checkbox>
							</Col>
						</Row>
						<br />
                        <Row gutter={15}>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                {this.renderInputList(['Voyage', 'Account', 'Commodity', 'Broker', 'Laycan'])}
                                {this.renderInputNumberList([ 'Quantity', 'Freight rate'])}
                                <br />
                                {this.renderRadioList(['LUMPSUM', 'Per MT Intake', 'Per LT Intake'], 'freight_rate_type', veerticalRadioStyle)}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <Row>
                                    <Col span={12}>
                                        <Input type='number' step='any' className='alignComponent' addonBefore='L/Rate' name='lrate' value={this.state.voyest.lrate} onChange={(event) => this.handleInputChange(event)}/>
                                    </Col>
                                    <Col offset={1} span={11}>
                                     {this.renderRadioList(['X','C','DAPS'],'load_rate_type')}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={12}>
                                        <Input type='number' step='any'  className='alignComponent' addonBefore='D/Rate' name='drate' value={this.state.voyest.drate} onChange={(event) => this.handleInputChange(event)}/>
                                    </Col>
                                    <Col offset={1} span={11}>
                                        {this.renderRadioList(['X','C','DAPS'],'discharge_rate_type')}
                                    </Col>
                                </Row>
                                {this.renderInputNumberList(['Comm.', 'Repos.'])}
                                <DatePicker addonBefore='Date' name='date' onChange={(date,dateString) => this.handleDateChange(date,dateString)} value={moment(this.state.voyest.date)}/>
                            </Col>
                        </Row>

                        <br />

                        <Row gutter={15}>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <h3>Ballast distance</h3>
                                {this.renderInputNumberList(['NON Seca (Ballast)', 'Seca (Ballast)'])}
                                <h3>Laden distance</h3>
                                {this.renderInputNumberList(['NON Seca (Laden)', 'Seca (Laden)', 'IFO price', 'MGO price', 'Lost/waiting days'])}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <h3>Port costs</h3>
                                <Row gutter={20}>
                                    <Col span={13}>
                                        <Input 
                                            type='number'
                                            step='any'
                                            className='alignComponent'
                                            addonBefore='Load' 
                                            name='load'
                                            onChange={(event) => this.handleInputChange(event)}
                                            value={this.state.voyest.load}/> 
                                    </Col>
                                    <Col span={11}>
                                        {this.renderRadioList(['Seca', 'NON Seca'],'load_port_type')}
                                    </Col>
                                </Row>
                                <Row gutter={20}>
                                    <Col span={13}>
                                         <Input 
                                            type='number'
                                            step='any'
                                            className='alignComponent'
                                            addonBefore='Disch' 
                                            name='disch'
                                            onChange={(event) => this.handleInputChange(event)}
                                            value={this.state.voyest.disch}/> 
                                    </Col>
                                    <Col span={11}>
                                        {this.renderRadioList(['Seca', 'NON Seca'],'discharge_port_type')}
                                    </Col>
                                </Row>
                                    {this.renderInputNumberList(['Others', 'Canals'])}
                                     <Input 
                                        type='number'
                                        step='any'
                                        className='alignComponent'
                                        addonBefore='Taxes %' 
                                        name='taxesP'
                                        onChange={(event) => this.handleInputChange(event)}
                                        value={this.state.voyest.taxesP}/> 
                                 {this.renderInputNumberList(['Miscel.', 'Exins', 'Extra costs'])}
                                 <Input 
                                    type='number'
                                    step='any'
                                    className='alignComponent'
                                    addonBefore='Extra costs 2' 
                                    name='extra_costs2'
                                    onChange={(event) => this.handleInputChange(event)}
                                    value={this.state.voyest.extra_costs2}/> 
                            </Col>
                        </Row>
                    </Col>

                    <Col  xs={24} sm={24} md={24} lg={24} xl={12}>

						<Row gutter={15}>
							<Col  xs={24} sm={12} md={12} lg={12} xl={12}>
							<h3>Vessel</h3>
							<span class="ant-input-group-wrapper">
								<span class="ant-input-wrapper ant-input-group">
									<span class="ant-input-group-addon">
										Vessel
									</span>
									<Select
										className='alignSelect'
										showSearch
										placeholder="Select a ship"
										optionFilterProp="children"
										value={this.state.voyest.vessel_id}
										onSelect={(id) => this.handleChangeSelect(id)}
										filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
											{vesselsOptions}
									</Select>
								</span>
							</span>
                                {this.renderInputNumberList(['Speed', 'IFO Ballast', 'IFO Laden', 'MGO Sea', 'IFO port idle', 'IFO port work', 'MGO port idle', 'MGO port work', 'Boiler port'])}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
								<h3>Voyage</h3>
                                {this.renderInputNumberList(['Load port', 'Disch. port', 'Steaming margin'])}
                                <h3>Days</h3>
                                {this.renderInputNumberList(['Steaming','Load days','Disch days', 'SHEX load', 'SHEX disch'], 'alignResultLightBlue', true)}
                                <Input 
                                    type='number'
                                    className='alignResultRed' 
                                    addonBefore='Total duration'
                                    name='total_duration'
                                    disabled={true}
                                    value={this.state.voyest.total_duration}/>
                            </Col>
                        </Row>

                        <h2>Results</h2>
                        {this.renderInputNumberList(['Gross revenue', 'Sailing bunkers', 'Loadport bunkers', 'Disport bunkers', 'Total bunker cost', 'Expenses', 'Commissions', 'Taxes', 'Exins', 'Net Revenue'], 'alignResultDarkBlue', true)}
                        <Input 
                            type='number'
                            className='alignResultRed' 
                            addonBefore='Time charter rate' 
                            name='time_charter_rate'
                            disabled={true}
                            value={this.state.voyest.time_charter_rate}/>
                        <Input 
                            type='number'
                            className='alignResultGreen' 
                            addonBefore='Sensitivity +/- $1'
                            name='sensitivity'
                            disabled={true}
                            value={this.state.voyest.sensitivity}/>
                        {downloadButtons}
                    </Col>
                </Row>
                <br />
                <br />
            </div>
			);
	}
}
export default withRouter(VoyageEstimate);



