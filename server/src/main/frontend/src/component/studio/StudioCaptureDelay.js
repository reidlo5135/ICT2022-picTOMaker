import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Button from '@mui/material/Button'

import { ThemeProvider } from '@mui/material';
import {theme} from './edittool/theme/MainTheme'
// d
export default function StudioCaptureDelay (props) {
    const setTimeCloseModal = props.setState;
    return (
        <> 
        <ThemeProvider theme = {theme}>
            <FormControl color = "button">
                <FormLabel id="demo-row-radio-buttons-group-label"><bold>촬영 지연시간</bold></FormLabel>
                <RadioGroup
                row
                aria-labelledby="demo-row-radio-buttons-group-label"
                name="row-radio-buttons-group"
                color = "button"
                >
                <FormControlLabel color = "button" value="none" control={<Radio color = "button" />} label="해제" />
                <FormControlLabel color = "button"value="3" control={<Radio color = "button" />} label="3초" />
                <FormControlLabel color = "button" value="5" control={<Radio  color = "button"/>} label="5초" />
                <FormControlLabel color = "button" value="10" control={<Radio color = "button" />} label="10초" />

                </RadioGroup>
            </FormControl>

            
            <Button sx={{display : "block", margin : "auto",marginTop: "20px"}} color="button" variant="contained" onClick={()=> {setTimeCloseModal(false)}}>확인</Button>
            </ThemeProvider>   
                 </>
    ) 
}